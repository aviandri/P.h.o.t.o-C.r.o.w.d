package controllers;

import models.User;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.Logger;
import play.Play;
import play.libs.OAuth;
import play.libs.OAuth.ServiceInfo;
import play.libs.OAuth.TokenPair;
import play.libs.WS;
import play.mvc.*;

public class Users extends Controller {
	
	private static final String twitterRequestTokenUrl = "https://api.twitter.com/oauth/request_token";
    private static final String twitterAuthorizeUrl = "https://api.twitter.com/oauth/authorize";
    private static final String twitterAccessTokenUrl = "https://api.twitter.com/oauth/access_token";
    
	public static void index() {
		render();
	}
	

	public static void authenticate(){
	   	if(OAuth.isVerifierResponse()){
	   		TokenPair tokens = OAuth.service(TWITTER).requestAccessToken(
	   				new TokenPair(session.get("twitter.token"), session.get("twitter.secret")));
	   		
		   	JsonElement json = WS.url("http://api.twitter.com/1/account/verify_credentials.json").oauth(TWITTER, tokens).get().getJson();
		   	Long twitterId = json.getAsJsonObject().getAsJsonPrimitive("id").getAsLong();
		   	User user = User.findByTwitterId(twitterId);
		   	if(user==null){
		   		user = new User();
		   		user.accessToken = tokens.token;
		   		user.secretToken = tokens.secret;
		   		user.twitterId = twitterId;
		   		user.save();
		   	}
		   	session.put("session.userid", user.id);
		   	Galleries.index();
		   	
	   	}
		
		OAuth twitt = OAuth.service(TWITTER);
	   	TokenPair tokens = twitt.requestUnauthorizedToken();
	   	session.put("twitter.secret", tokens.secret);
	   	session.put("twitter.token", tokens.token);
	   	System.out.println("secret first :"+ tokens.secret);
	   	System.out.println("token first :"+ tokens.token);
	   	redirect(twitt.redirectUrl(tokens));
	  
   }

	private static ServiceInfo TWITTER = new ServiceInfo(
		twitterRequestTokenUrl, twitterAccessTokenUrl, twitterAuthorizeUrl,
			Play.configuration.getProperty("twitter.consumer.key"),
			Play.configuration.getProperty("twitter.consumer.secret"));

}
