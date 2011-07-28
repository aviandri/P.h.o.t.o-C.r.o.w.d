package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.URL;

@Entity
public class Photo extends Model {
    @ManyToOne
    @JoinColumn(name = "gallery_id")
    public Gallery gallery;

    @Column(name = "poster_user_name")
    public String posterUserName;

    @Column(name = "tweet_content")
    public String tweetContent;

    @URL
    @Column(name = "full_image_url")
    public String fullImageURL;

    @URL
    @Column(name = "thumb_image_url")
    public String thumbImageURL;

}
