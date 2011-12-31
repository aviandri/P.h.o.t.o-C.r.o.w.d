package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jobs.RetrievePhotoUrlJob;

import play.Logger;
import play.data.validation.URL;
import utils.photoservice.ImageAndThumbnailUrlHolder;
import utils.photoservice.PhotoServices;
import utils.photoservice.PhotoServices.PhotoResource;

@Entity
public class Photo extends Model {
    private static final int TEN_SECONDS = 10000;

    @ManyToOne
    @JoinColumn(name = "gallery_id")
    public Gallery gallery;

    @ManyToOne(optional = false)
    @JoinColumn(name = "poster_id")
    public User poster;

    @Column(name = "message")
    public String message;
    
    @URL
    @Column(name = "original_url", length = 500)
    public String originalUrl;
    
    @URL
    @Column(name = "full_image_url", length = 500)
    public String fullImageUrl;

    @URL
    @Column(name = "thumb_image_url", length = 500)
    public String thumbImageUrl;
    
    @Column(name = "expires")
    public Long expires;
    
    @Column(name = "reference_id", nullable = false)
    public Long referenceId;
    
    @Column(name = "reference_date", nullable = false)
    public Date referenceDate;
    
    public boolean hasExpired() {
        return hasExpired(System.currentTimeMillis());
    }
    
    public boolean hasExpired(long currentTimeMillis) {
        return expires != null && currentTimeMillis > expires;
    }
    
    public static List<Photo> findByGalleryAndRevalidate(Gallery gallery, Long startId, Long endId, int limit){
    	startId  = startId > 0 ? startId : 0;
    	endId = endId > 0 ? endId : startId + 50;
    	List<Photo> photos = Photo.find("gallery = ? AND id > ? AND id < ? order by id DESC", gallery, startId, endId).fetch(limit);
    	for (Photo photo : photos) {
    	    if (photo.hasExpired(System.currentTimeMillis() + TEN_SECONDS)) {
    	        revalidate(photo);
    	    }
    	}
    	return photos;
    }
    
    public static Photo findGalleryRepresentation(Gallery gallery) {
        Photo photo = Photo.find("gallery = ?", gallery).first();
        if (photo != null && photo.hasExpired(System.currentTimeMillis() + TEN_SECONDS)) {
            revalidate(photo);
        }
        return photo;
    }
    
    public static Photo findByIdAndRevalidate(Long id) {
        Photo photo = findById(id);
        if (photo != null && photo.hasExpired(System.currentTimeMillis() + TEN_SECONDS)) {
            revalidate(photo);
        }
        return photo;
    }
    
    private static void revalidate(Photo photo) {
        PhotoResource res = PhotoServices.photoResource(photo.originalUrl);
        if (res == null) {
            Logger.warn("Cannot recognize the photo url %1s", photo.originalUrl);
            return;
        }
        
        ImageAndThumbnailUrlHolder holder = res.grab();
        if (holder == null) {
            Logger.warn("Failed getting the image and thumbnail for %1s", photo.originalUrl);
            return;
        }
        
        if (Logger.isDebugEnabled()) {
            Logger.debug("Saving new photo url for %1s", photo.originalUrl);
        }
        photo.fullImageUrl = holder.url;
        photo.thumbImageUrl = holder.thumbUrl;
        photo.expires = holder.expires;
        photo.save();
    }
}
