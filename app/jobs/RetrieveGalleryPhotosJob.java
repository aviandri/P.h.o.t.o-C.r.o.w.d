package jobs;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import models.Gallery;
import models.Gallery.State;
import models.User;
import play.Logger;
import play.Play;
import play.jobs.Job;
import play.templates.JavaExtensions;
import utils.Twitter;
import utils.Twitter.QueryBuilder;
import utils.Twitter.QueryResult;
import utils.photoservice.PhotoServices;
import utils.photoservice.PhotoServices.PhotoResource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * This job responsible to take all the tweets related to the gallery and end up
 * with getting the photo URL on the tweet.
 * 
 * @author uudashr@gmail.com
 * 
 */
public class RetrieveGalleryPhotosJob extends Job<Void> {
    private Gallery gallery;

    public RetrieveGalleryPhotosJob(Gallery crowdGallery) {
        this.gallery = crowdGallery;
    }

    @Override
    public void doJob() throws Exception {
        String searchQuery = buildQuery(gallery);
        Logger.debug("Searching '%1s'", searchQuery);
        final int rpp = Integer.parseInt(Play.configuration.getProperty(
                "twitter.search.rpp", "15"));
        if (gallery.state == State.NEW) {
            QueryResult res = Twitter.query(searchQuery)
                    .sinceId(0).rpp(rpp)
                    .includeEntities(true)
                    .execute();
            
            for (JsonElement tweet : res.getTweets()) {
                try {
                    processTweet(tweet);
                } catch (ReachStopIdException e) {
                    Logger.warn(e, "Should never reach stopId");
                }
            }
            
            gallery = Gallery.findById(this.gallery.id);
            
            if (res.hasNextPage()) {
                gallery.maxId = res.getMaxId();
                gallery.lastPage = res.getPage();
                gallery.state = State.FETCH_OLDER;
            } else {
                gallery.stopId = res.getMaxId();
                gallery.state = State.FETCH_YOUNGER;
            }
            Logger.debug("Switch to %1s", gallery.state);
            
            gallery.save();
        } else if (gallery.state == State.FETCH_OLDER) {
            int newPage = gallery.lastPage + 1;
            Logger.debug("Query maxId=%1s page=%2s rpp=%3s", gallery.maxId,
                    newPage, rpp);
            QueryResult res = Twitter.query(searchQuery)
                    .maxId(gallery.maxId)
                    .page(newPage)
                    .rpp(rpp)
                    .includeEntities(true)
                    .execute();
            
            for (JsonElement tweet : res.getTweets()) {
                try {
                    processTweet(tweet);
                } catch (ReachStopIdException e) {
                    Logger.debug("Already reach stopId");
                    gallery = Gallery.findById(this.gallery.id);
                    gallery.stopId = res.getMaxId();
                    gallery.state = State.FETCH_YOUNGER;
                    gallery.save();
                    return;
                }
            }
            
            gallery = Gallery.findById(this.gallery.id);
            
            if (res.hasNextPage()) {
                gallery.lastPage = newPage;
            } else {
                gallery.stopId = res.getMaxId();
                gallery.state = State.FETCH_YOUNGER;
            }
            Logger.debug("Switch to %1s", gallery.state);
            
            gallery.save();
        } else if (gallery.state == State.FETCH_YOUNGER) {
            QueryResult res = Twitter.query(searchQuery)
                    .sinceId(gallery.stopId)
                    .rpp(rpp)
                    .includeEntities(true)
                    .execute();
            boolean passEndDate = new Date().after(gallery.endDate);
            
            
            for (JsonElement tweet : res.getTweets()) {
                try {
                    processTweet(tweet);
                } catch (ReachStopIdException e) {
                    Logger.debug("Already reach stopId");
                    gallery = Gallery.findById(this.gallery.id);
                    if (passEndDate) {
                        gallery.state = State.DONE;
                    } else {
                        gallery.stopId = res.getMaxId();
                        gallery.state = State.FETCH_YOUNGER;
                    }
                    gallery.save();
                    return;
                }
            }
            
            gallery = Gallery.findById(this.gallery.id);
            
            if (res.hasNextPage()) {
                gallery.maxId = res.getMaxId();
                gallery.lastPage = res.getPage();
                gallery.state = State.FETCH_OLDER;
            } if (passEndDate) {
                gallery.state = State.DONE;
            } else {
                gallery.stopId = res.getMaxId();
            }
            
            Logger.debug("Switch to %1s", gallery.state);
            
            gallery.save();
        }
        
    }
    
    /**
     * Get the query part to filter tweets only for the available photo service
     * only.
     * 
     * @return the query part.
     */
    private static final String photoServiceQueryPart() {
        String[] prefixes = PhotoServices.getSearchKeys();
        return "(" + JavaExtensions.join(Arrays.asList(prefixes), " OR ") + ")";
    }
    
    /**
     * Build the query based on the given <tt>Gallery</tt>.
     * 
     * @param gallery is the <tt>Gallery</tt>.
     * @return the query.
     */
    private static String buildQuery(Gallery gallery) {
        
        QueryBuilder queryBuilder = new QueryBuilder(
                "#" + gallery.hashtag 
                + " " + photoServiceQueryPart() + " -RT");
        
        if (gallery.startDate != null) {
            queryBuilder.since(gallery.startDate);
            
            if(Play.configuration.getProperty("twitter.search.until.enabled", "true").equals("true")) {
                if (gallery.endDate != null) {
                    queryBuilder.until(gallery.endDate);
                }
            }
        }
        
        if (gallery.location != null && gallery.location.trim().length() != 0) {
            queryBuilder.near(gallery.location);
        }
        return queryBuilder.toString();
    }
    
    /**
     * Process the tweet from the given tweet entry.
     * 
     * @param tweet is the tweet entry.
     * @throws ReachStopIdException if the id is lower than gallery.stopId.
     */
    private void processTweet(JsonElement tweet) throws ReachStopIdException {
        JsonObject tweetObject = tweet.getAsJsonObject();
        
        long id = tweetObject.getAsJsonPrimitive("id").getAsLong();
        String tweetText = tweetObject.getAsJsonPrimitive("text").getAsString();
        String username = tweetObject.getAsJsonPrimitive("from_user")
                .getAsString();
        long twitterId = tweetObject.getAsJsonPrimitive("from_user_id").getAsLong();
        String createdDateStr = tweetObject.getAsJsonPrimitive("created_at")
                .getAsString();
        
        JsonObject entitiesObject = tweetObject.getAsJsonObject("entities");
        JsonArray urlsObject = entitiesObject.getAsJsonArray("urls");
        
        User user = User.findByTwitterId(twitterId);
        if (user == null) {
            user = new User(twitterId, username).save();
        }
        
        DateFormat dateFormat = newDateFormat();
        Date createdDate = null;
        try {
            createdDate = dateFormat.parse(createdDateStr);
        } catch (ParseException e) {
            Logger.error("Invalid date format of created_date %1s", createdDateStr);
            return;
        }
        
        if (id < gallery.stopId) {
            throw new ReachStopIdException("The tweet already reach the stopId");
        }
        
        if (gallery.endDate != null && createdDate.after(gallery.endDate)) {
            // skip the endDate since we can disabled/not using the twitter
            // "until" operator
            return;
        }
        
        // all URLs on the tweet
        String[] urls = new String[urlsObject.size()];
        for (int i = 0; i < urlsObject.size(); i++) {
            String url = urlsObject.get(i).getAsJsonObject().getAsJsonPrimitive("expanded_url").getAsString();
            urls[i] = url;
        }
        
        PhotoResource[] tweetPhotos = PhotoServices.filterToPhotoResources(urls);
        if (tweetPhotos != null) {
            Logger.debug("Found recognize url from tweet: %1s", tweetText);
            for (PhotoResource tweetPhoto : tweetPhotos) {
                new RetrievePhotoUrlJob(gallery, tweetPhoto, user.id, tweetText, id).now();
            }
        }
    }
    
    /**
     * Create the date formatter compatible with twitter date.
     * 
     * @return the date formatter.
     */
    private static DateFormat newDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss ZZZZZZ", Locale.US);
        dateFormat.setLenient(false);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat;
    }
    
    
    /**
     * This exception thrown if the processed tweet already reach the stopId.
     * 
     * @author uudashr@gmail.com
     *
     */
    private static class ReachStopIdException extends Exception {
        public ReachStopIdException(String message) {
            super(message);
        }
    }
}
