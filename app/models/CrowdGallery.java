package models;

import play.*;
import play.data.validation.Required;
import utils.TwitterUtil;

import javax.persistence.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;

@Entity
public class CrowdGallery extends Model {
    @Required
	public String hashtag;
    @Required
	public String name;
    @Required
	public String location;
    @Required
    public User creator;
    @Column(name="last_id")
    public Long lastId=0L;
    public boolean state=true;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "crowdgallery_id")
    public Set<CrowdPhoto> crowdPhotos = new HashSet<CrowdPhoto>();
    @ManyToOne
    public User user;
    
    
    public List<CrowdPhoto> getPhotos(){
    	Logger.debug("fetch gallery photos");
    	return CrowdPhoto.find("byCrowdGallery", this).fetch();
    	
    }
    
    public long getPhotosCount(){
    	return CrowdPhoto.count("byCrowdGallery", this);
    }
    
   
}
