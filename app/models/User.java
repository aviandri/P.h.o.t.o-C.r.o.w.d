package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.data.validation.Required;

@Entity
public class User extends Model {
    @Required
    @Column(nullable=false)
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
    
    public static User findByUsername(String userName){
    	return User.find("byUsername", userName).first();
    }
    
    public static User findByTwitterId(Long twitterId){
    	return User.find("byTwitterId", twitterId).first();
    }
    
    
    
}
