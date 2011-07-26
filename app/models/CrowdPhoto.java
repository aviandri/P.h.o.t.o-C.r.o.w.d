package models;

import play.*;

import javax.persistence.*;

import java.net.URL;
import java.util.*;

@Entity
public class CrowdPhoto extends Model {
    @ManyToOne
	public CrowdGallery crowd;
    @Column(name="poster_user_name")
    public String posterUserName;
    @Column(name="tweet_content")
    public String tweetContent;
    @Column(name="full_image_url")
    public URL fullImageURL;
    @Column(name="thumb_image_url")
    public URL thumbImageURL;
    @ManyToOne
    public CrowdGallery crowdGallery;
    
    
}
