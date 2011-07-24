package models;

import javax.persistence.Entity;

@Entity
public class User extends Model {
    public String username;
    public String secretToken;
    public String accessToken;
    public Long twitterId;
    public String profileImageUrl;
    
    public static User findByUsername(String userName){
    	return User.find("byUsername", userName).first();
    }
    
    public static User findByTwitterId(Long twitterId){
    	return User.find("byTwitterId", twitterId).first();
    }
    
    
    
}
