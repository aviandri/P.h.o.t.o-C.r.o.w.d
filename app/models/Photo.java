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

    @ManyToOne(optional = false)
    @JoinColumn(name = "poster_id")
    public User poster;

    @Column(name = "tweet_content")
    public String tweetContent;

    @URL
    @Column(name = "full_image_url")
    public String fullImageUrl;

    @URL
    @Column(name = "thumb_image_url")
    public String thumbImageUrl;

}
