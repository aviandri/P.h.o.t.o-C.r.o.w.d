package jobs;

import models.Gallery;
import models.Photo;
import models.User;
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
    private Long posterId;
    private String tweetText;
    private long referenceId;

    public RetrievePhotoUrlJob(Gallery gallery, PhotoResource tweetPhoto, Long posterId,
            String tweetText, long referenceId) {
        this.gallery = gallery;
        this.photoResource = tweetPhoto;
        this.posterId = posterId;
        this.tweetText = tweetText;
        this.referenceId = referenceId;
    }

    public void doJob() throws Exception {
        Logger.debug("Get photo from URL %1s", photoResource.url);
        ImageAndThumbnailUrlHolder imageUrlHolder = photoResource.grab();
        if (imageUrlHolder == null) {
            Logger.warn("Cannot get photo URL from %1s ... skip", photoResource.url);
            return;
        }
        
        Photo photo = new Photo();
        photo.fullImageUrl = imageUrlHolder.url;
        photo.thumbImageUrl = imageUrlHolder.thumbUrl;
        photo.poster = User.findById(posterId);
        photo.message = tweetText;
        photo.gallery = gallery;
        photo.referenceId = referenceId;
        photo.save();
    }

}
