package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;

@Entity
public class Gallery extends Model {
    @Required
    @Column(nullable=false)
	public String hashtag;
    
    @Column(name="last_id")
    public Long lastId = 0L;
    
    public Boolean state = true;
    
    @ManyToOne
    public User user;
    
}
