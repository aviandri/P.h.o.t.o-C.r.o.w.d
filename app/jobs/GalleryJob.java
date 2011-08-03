package jobs;

import java.io.UnsupportedEncodingException;

import models.Gallery;
import models.Gallery.State;
import play.Logger;
import play.jobs.Job;
import utils.StringUtils;
import utils.Twitter;
import utils.Twitter.QueryBuilder;
import utils.Twitter.QueryResult;

import com.google.gson.JsonArray;
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
        if (gallery.state == State.NEW) {
            Logger.debug("Query sinceId=0");
            QueryResult res = Twitter.query(searchQuery).sinceId(0L).execute();
    
            JsonArray tweets = res.getTweets();
            for (JsonElement tweet : tweets) {
                processTweet(tweet);
            }
            
            gallery = Gallery.findById(this.gallery.id);
            
            gallery.maxId = res.getMaxId();
            
            System.out.println(res.getJsonObject().get("max_id"));
            System.out.println(res.getJsonObject().get("next_page"));
            
            if (res.hasNextPage()) {
                gallery.state = State.FETCH_OLDER;
                gallery.lastPage = res.getPage();
            } else {
                gallery.state = State.FETCH_YOUNGER;
            }
            Logger.debug("Switch to %1s", gallery.state);
            
            gallery.save();
        } else if (gallery.state == State.FETCH_OLDER) {
            int newPage = gallery.lastPage + 1;
            Logger.debug("Query maxId=%1s page=%2s rpp=%3s", gallery.maxId, newPage, 100);
            QueryResult res = Twitter.query(searchQuery).maxId(gallery.maxId).page(newPage).rpp(100).execute();
            
            gallery = Gallery.findById(this.gallery.id);
            
            if (res.hasNextPage()) {
                gallery.lastPage = newPage;
            } else {
                gallery.state = State.FETCH_YOUNGER;
            }
            Logger.debug("Switch to %1s", gallery.state);
            
            gallery.save();
        } else if (gallery.state == State.FETCH_YOUNGER) {
            // TODO -> NOT IMPLEMENTED YET
            gallery = Gallery.findById(this.gallery.id);
            
            gallery.state = State.DONE;
            
            Logger.debug("Switch to %1s", gallery.state);
            
            gallery.save();
        }
        
        //saveTweetLastId(tweets);
    }
    
    private static String buildQuery(Gallery gallery) throws UnsupportedEncodingException {
        QueryBuilder queryBuilder = new QueryBuilder("#" + gallery.hashtag);
        
        if (gallery.startDate != null) {
            queryBuilder.since(gallery.startDate);
            if (gallery.endDate != null) {
                queryBuilder.until(gallery.endDate);
            }
        }
        
        if (gallery.location != null && gallery.location.trim().length() != 0) {
            queryBuilder.near(gallery.location);
        }
        return queryBuilder.toString();
    }
    
    private void processTweet(JsonElement tweet) {
        JsonObject tweetObject = tweet.getAsJsonObject();
        String tweetText = tweetObject.getAsJsonPrimitive("text").getAsString();
        String username = tweetObject.getAsJsonPrimitive("from_user")
                .getAsString();
        Logger.debug("tweet text:" + tweetText);
        String[] urls = StringUtils.grabImageServiceURLs(tweetText);
        for (String url : urls) {
            initPhotoJob(tweetText, username, url);
        }
    }

    private void initPhotoJob(String tweetText, String username, String url) {
        GrabPhotoJob photoJob = new GrabPhotoJob(gallery, url, username, tweetText);
        photoJob.now();
    }

}
