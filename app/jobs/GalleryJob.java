package jobs;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import models.Gallery;
import models.Gallery.State;
import play.Logger;
import play.Play;
import play.jobs.Job;
import utils.StringUtils;
import utils.Twitter;
import utils.Twitter.QueryBuilder;
import utils.Twitter.QueryResult;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GalleryJob extends Job<Void> {
    private Gallery gallery;

    public GalleryJob(Gallery crowdGallery) {
        this.gallery = crowdGallery;
    }

    @Override
    public void doJob() throws Exception {
        String searchQuery = buildQuery(gallery);
        Logger.debug("Searching '%1s'", searchQuery);
        final int rpp = Integer.parseInt(Play.configuration.getProperty("twitter.search.rpp", "15"));
        if (gallery.state == State.NEW) {
            QueryResult res = Twitter.query(searchQuery).sinceId(0).rpp(rpp).execute();
            
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
            Logger.debug("Query maxId=%1s page=%2s rpp=%3s", gallery.maxId, newPage, rpp);
            QueryResult res = Twitter.query(searchQuery).maxId(gallery.maxId).page(newPage).rpp(100).execute();
            
            for (JsonElement tweet : res.getTweets()) {
                try {
                    processTweet(tweet);
                } catch (ReachStopIdException e) {
                    Logger.debug("Already reach stopId");
                    gallery = Gallery.findById(this.gallery.id);
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
            QueryResult res = Twitter.query(searchQuery).sinceId(gallery.maxId).rpp(rpp).execute();
            
            for (JsonElement tweet : res.getTweets()) {
                try {
                    processTweet(tweet);
                } catch (ReachStopIdException e) {
                    Logger.debug("Already reach stopId");
                    gallery = Gallery.findById(this.gallery.id);
                    gallery.state = State.FETCH_YOUNGER;
                    gallery.save();
                    return;
                }
            }
            
            gallery = Gallery.findById(this.gallery.id);
            
            if (res.hasNextPage()) {
                gallery.maxId = res.getMaxId();
                gallery.lastPage = res.getPage();
                gallery.state = State.FETCH_OLDER;
            } if (new Date().after(gallery.endDate)) {
                gallery.state = State.DONE;
            } else {
                gallery.stopId = res.getMaxId();
            }
            
            Logger.debug("Switch to %1s", gallery.state);
            
            gallery.save();
        }
        
        //saveTweetLastId(tweets);
    }
    
    private static String buildQuery(Gallery gallery) throws UnsupportedEncodingException {
        QueryBuilder queryBuilder = new QueryBuilder("#" + gallery.hashtag + " (twitpic OR lockerz OR twitgoo) -RT");
        
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
    
    private void processTweet(JsonElement tweet) throws ReachStopIdException {
        JsonObject tweetObject = tweet.getAsJsonObject();
        
        long id = tweetObject.getAsJsonPrimitive("id").getAsLong();
        String tweetText = tweetObject.getAsJsonPrimitive("text").getAsString();
        String username = tweetObject.getAsJsonPrimitive("from_user")
                .getAsString();
        String createdDateStr = tweetObject.getAsJsonPrimitive("created_at")
                .getAsString();
        
        
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
            Logger.debug("Found tweet %1s passed the end date (%2s vs %3s).. skip", id, createdDateStr, dateFormat.format(gallery.endDate));
            return;
        }
        
        String[] urls = StringUtils.grabImageServiceURLs(tweetText);
        for (String url : urls) {
            initPhotoJob(tweetText, username, url);
        }
    }

    private void initPhotoJob(String tweetText, String username, String url) {
        GrabPhotoJob photoJob = new GrabPhotoJob(gallery, url, username, tweetText);
        photoJob.now();
    }
    
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
