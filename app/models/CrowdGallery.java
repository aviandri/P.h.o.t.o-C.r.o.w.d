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
    public User creator;
    @Column(name="last_id")
    public Long lastId=0L;
    public boolean state=true;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<CrowdPhoto> crowdPhotos = new HashSet<CrowdPhoto>();
    @ManyToOne
    public User user;
    
    
    public static void save(CrowdGallery crowd){
    	crowd.save();
    }
    
    
    public static String test(){
    	JsonArray array = TwitterUtil.searchTwitter("t", 1L);
    	JsonObject jObject = array.get(0).getAsJsonObject();
    	return jObject.getAsJsonPrimitive("test").getAsString();
    }
}
