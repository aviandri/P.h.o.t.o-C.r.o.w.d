package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Match;
import play.data.validation.Required;

@Entity
public class Gallery extends Model {

    @Required
    @Column(nullable = false)
    public String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    public Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    public Date endDate;

    @Required
    @Match(value = "#?([A-Za-z0-9_]+) *")
    @Column(nullable = false)
    public String hashtag;

    public String location;

    public String description;

    @Column(nullable = false, name = "last_id")
    public Long lastId = 0L;

    @Column(nullable = false)
    public Boolean state = true;

    @Required
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    public Gallery() {
    }

    public Gallery(String name, Date startDate, Date endDate, String hashtag,
            String location, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hashtag = hashtag;
        this.location = location;
        this.description = description;
    }
    
    public List<Photo> getPhotos() {
        return Photo.find("byGallery", this).fetch();
    }
    
    public long getPhotosCount() {
        return Photo.count("byGallery", this);
    }

}
