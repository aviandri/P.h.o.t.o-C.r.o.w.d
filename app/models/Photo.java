package models;

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

    @Column(name = "tweet_content")
    public String tweetContent;

    @URL
    @MaxSize(500)
    @Column(name = "full_image_url", length = 500)
    public String fullImageUrl;

    @URL
    @MaxSize(500)
    @Column(name = "thumb_image_url", length = 500)
    public String thumbImageUrl;

}
