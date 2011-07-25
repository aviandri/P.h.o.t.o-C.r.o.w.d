package controllers;

import java.io.IOException;
import java.net.URL;

import jobs.GalleryManagerJob;
import play.mvc.Controller;
import utils.TweetPhotoFactory;
import utils.TweetPhotoGrabber;
import controllers.Secure.Security;

public class Application extends Controller {

    public static void index() {
		if(Security.isConnected()){
			Users.myGalleries();
		}
    	render();
    }
    
    public static void home() {
        render();
    }
    
    public static void show() throws IOException{
    	TweetPhotoGrabber grabber = TweetPhotoFactory.create(new URL("http://twitpic.com/2giosz"));
    	System.out.println(grabber.getFullImageURL());
    	renderText("OK");
    }
    
    public static void test() throws IOException{
    	GalleryManagerJob job = new GalleryManagerJob();
    	job.run();
    }
}