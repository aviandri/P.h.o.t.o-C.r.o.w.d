package models;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Match;
import play.data.validation.Required;

@Entity
public class Gallery extends Model {
    public enum State {
        NEW, FETCH_OLDER, FETCH_YOUNGER, DONE
    }
    
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
    @Match(value = "[A-Za-z0-9_]+")
    @Column(nullable = false)
    public String hashtag;

    public String location;

    public String description;
    
    @Required
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
    
    @Column(name = "max_id")
    public Long maxId;
    
    @Column(name = "stop_id", nullable = false)
    public Long stopId = 0L;
    
    @Column(name = "last_page")
    public Integer lastPage;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public State state = State.NEW;

    @OneToMany(mappedBy = "gallery", fetch = FetchType.LAZY)
    public Set<Photo> photos;
    
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
    
    public Photo getSnapPhoto() {
        return Photo.findGallerySnap(this);
    }
}
