package jobs;

import models.CrowdGallery;
import play.Logger;
import play.jobs.Job;
import utils.StringUtils;
import utils.TwitterUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GalleryJob extends Job<Void> {
    private CrowdGallery crowdGallery;

    public GalleryJob(CrowdGallery crowdGallery) {
        this.crowdGallery = crowdGallery;
    }

    @Override
    public void doJob() throws Exception {
        JsonArray results = TwitterUtil.searchTwitter("#"
                + crowdGallery.hashtag, crowdGallery.lastId);

        Logger.debug("tweet search result:" + results);
        for (JsonElement tweet : results) {
            processTweets(tweet);
        }
        saveTweetLastId(results);
    }

    private void saveTweetLastId(JsonArray results) {
        if (results.size() < 0) {
            return;
        }
        Long lastId = Long.parseLong(results.get(0).getAsJsonObject()
                .getAsJsonPrimitive("id_str").getAsString());
        Logger.debug("Tweet last Id" + lastId);
        Logger.debug("crowd gallery id" + crowdGallery.id);

        CrowdGallery crowd = CrowdGallery.findById(this.crowdGallery.id);
        Logger.info("Crowd gallery" + crowd);
        if (lastId == null) {
            return;
        }
        crowd.lastId = lastId;
        crowd.save();
    }

    private void processTweets(JsonElement tweet) {
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
        PhotoJob photoJob = new PhotoJob(crowdGallery, url, username, tweetText);
        photoJob.now();
    }

}
