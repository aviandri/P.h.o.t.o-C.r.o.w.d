package models;

import play.*;
import play.libs.OAuth;
import play.libs.OAuth.ServiceInfo;

import javax.mail.Session;
import javax.persistence.*;

import java.util.*;

@Entity
public class User extends Model {
    public String username;
    public String secretToken;
    public String accessToken;
    public Long twitterId;
    @OneToMany(cascade = CascadeType.ALL)
    public Set<CrowdGallery> crowdGalleries = new HashSet<CrowdGallery>();
    
    public static User findByUsername(String userName){
    	return User.find("byUsername", userName).first();
    }
    
    public static User findByTwitterId(Long twitterId){
    	return User.find("byTwitterId", twitterId).first();
    }
    
    
    
}
