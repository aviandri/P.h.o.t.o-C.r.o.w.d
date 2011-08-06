package jobs;

import models.Gallery;
import models.Photo;
import play.Logger;
import play.jobs.Job;
import utils.photoservice.ImageAndThumbnailUrlHolder;
import utils.photoservice.PhotoServices.PhotoResource;

/**
 * This job responsible to grab the real photo URL from related photo service.
 * 
 * @author uudashr@gmail.com
 * 
 */
public class RetrievePhotoUrlJob extends Job<Void> {
    private Gallery gallery;
    private PhotoResource photoResource;
    private String username;
    private String tweetText;

    public RetrievePhotoUrlJob(Gallery gallery, PhotoResource tweetPhoto, String username,
            String tweetText) {
        this.gallery = gallery;
        this.photoResource = tweetPhoto;
        this.username = username;
        this.tweetText = tweetText;
    }

    public void doJob() throws Exception {
        Logger.debug("Get photo from URL %1s", photoResource.url);
        ImageAndThumbnailUrlHolder imageUrlHolder = photoResource.grab();
        if (imageUrlHolder == null) {
            Logger.warn("Cannot get photo URL from %1s ... skip", photoResource.url);
        }
        
        Photo photo = new Photo();
        photo.fullImageURL = imageUrlHolder.url;
        photo.thumbImageURL = imageUrlHolder.thumbUrl;
        photo.posterUserName = username;
        photo.tweetContent = tweetText;
        photo.gallery = gallery;
        photo.save();
    }

}
