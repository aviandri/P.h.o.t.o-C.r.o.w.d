package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.data.validation.Required;

@Entity
public class User extends Model {
    @Required
    @Column(nullable=false, unique=true)
    public String username;
    
    @Required
    @Column(nullable=false)
    public String secretToken;
    
    @Required
    @Column(nullable=false)
    public String accessToken;
    
    @Required
    @Column(nullable=false)
    public Long twitterId;
    
    public String profileImageUrl;
    
    public String profileImageMiniUrl;
    
    public String profileImageBiggerUrl;
    
    public String profileImageOriginalUrl;
    
    public static User findByUsername(String username){
    	return User.find("byUsername", username).first();
    }
    
    public static User findByTwitterId(Long twitterId){
    	return User.find("byTwitterId", twitterId).first();
    }
    
    
    
}
