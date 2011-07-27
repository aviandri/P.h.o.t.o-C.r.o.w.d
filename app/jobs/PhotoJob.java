package jobs;

import java.net.URL;

import models.Gallery;
import models.Photo;
import play.jobs.Job;
import utils.TweetPhotoFactory;
import utils.TweetPhotoGrabber;

public class PhotoJob extends Job<Void> {
    private Gallery gallery;
    private String url;
    private String username;
    private String tweetText;

    public PhotoJob(Gallery gallery, String url, String username,
            String tweetText) {
        this.gallery = gallery;
        this.url = url;
        this.username = username;
        this.tweetText = tweetText;
    }

    public void doJob() throws Exception {
        TweetPhotoGrabber grabber = TweetPhotoFactory.create(new URL(url));
        Photo photo = new Photo();
        photo.fullImageURL = grabber.getFullImageURL().toString();
        photo.thumbImageURL = grabber.getThumbImageURL().toString();
        photo.posterUserName = username;
        photo.tweetContent = tweetText;
        photo.gallery = gallery;

        photo.save();
    }

}
