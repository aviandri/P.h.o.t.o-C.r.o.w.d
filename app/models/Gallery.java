package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Match;
import play.data.validation.Required;

@Entity
public class Gallery extends Model {
    
    @Required
    @Column(nullable=false)
    public String name;
    
    @Temporal(TemporalType.DATE)
    public Date startDate;
    
    @Temporal(TemporalType.DATE)
    public Date endDate;
    
    @Required
    @Match(value="#?([A-Za-z0-9_]+) *")
    @Column(nullable=false)
	public String hashtag;
    
    public String location;
    
    public String description;
    
    @Column(name="last_id")
    public Long lastId = 0L;
    
    public Boolean state = true;
    
    @ManyToOne
    public User user;
    
    public Gallery() {
    }
    
    public Gallery(String name, Date startDate, Date endDate,
            String hashtag, String location, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hashtag = hashtag;
        this.location = location;
        this.description = description;
    }
    
}
