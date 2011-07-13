package models;

import play.*;
import play.libs.OAuth.TokenPair;

import javax.persistence.*;
import java.util.*;

@Entity
public class User extends Model {
    public String username;
    public String requestToken;
    public String accessToken;
    
    public TokenPair getTokenPair(){
    	TokenPair tokenPair = new TokenPair(requestToken, accessToken);
    	return tokenPair;
    }
    
    public static User findByUsername(String userName){
    	return User.find("byUsername", userName).first();
    }
}
