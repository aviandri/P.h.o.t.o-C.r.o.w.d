package controllers;

import models.Photo;
import play.mvc.*;
import utils.Twitter;
import utils.Twitter.TwitterUser;

@With(Secure.class)
public class Photos extends Controller {

    public static void index() {
        render();
    }
    
    public static void details(Long id){
    	Photo photo = Photo.findById(id);
    	TwitterUser twitUser = Twitter.getTwitterUser(photo.poster.twitterId);
    	String userImageUrl = twitUser.getProfileImageUrl();
    	render(photo, userImageUrl);    	
    }

}
