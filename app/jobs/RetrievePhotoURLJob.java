package jobs;

import models.Gallery;
import models.Photo;
import play.Logger;
import play.jobs.Job;
import utils.photograbber.TweetPhotoFactory;
import utils.photograbber.TweetPhotoGrabber;

/**
 * This job responsible to grab the real photo URL from related photo service.
 * 
 * @author uudashr@gmail.com
 * 
 */
public class RetrievePhotoURLJob extends Job<Void> {
    private Gallery gallery;
    private String url;
    private String username;
    private String tweetText;

    public RetrievePhotoURLJob(Gallery gallery, String url, String username,
            String tweetText) {
        this.gallery = gallery;
        this.url = url;
        this.username = username;
        this.tweetText = tweetText;
    }

    public void doJob() throws Exception {
        Logger.debug("Get photo from URL %1s", url);
        TweetPhotoGrabber grabber = TweetPhotoFactory.create(url);
        Photo photo = new Photo();
        photo.fullImageURL = grabber.getFullImageURL();
        photo.thumbImageURL = grabber.getThumbImageURL();
        photo.posterUserName = username;
        photo.tweetContent = tweetText;
        photo.gallery = gallery;
        
        if (photo.fullImageURL == null || photo.thumbImageURL == null) {
            Logger.warn("Cannot get photo URl from %1s ... skip", url);
            return;
        }
        photo.save();
    }

}
