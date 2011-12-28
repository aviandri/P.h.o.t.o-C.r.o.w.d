package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.MaxSize;
import play.data.validation.URL;

@Entity
public class Photo extends Model {
    @ManyToOne
    @JoinColumn(name = "gallery_id")
    public Gallery gallery;

    @ManyToOne(optional = false)
    @JoinColumn(name = "poster_id")
    public User poster;

    @Column(name = "message")
    public String message;

    @URL
    @Column(name = "full_image_url", length = 500)
    public String fullImageUrl;

    @URL
    @Column(name = "thumb_image_url", length = 500)
    public String thumbImageUrl;
    
    @Column(name = "reference_id", nullable = false)
    public Long referenceId;
    
    
    public static List<Photo> findByGallery(Gallery gallery, Long startId, Long endId, int limit){
    	startId  = startId > 0 ? startId : 0;
    	endId = endId > 0 ? endId : startId + 50;
    	return Photo.find("gallery = ? AND id > ? AND id < ? order by id DESC", gallery, startId, endId).fetch(limit);    	
    }

}
