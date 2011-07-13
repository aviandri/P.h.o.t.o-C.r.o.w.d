package jobs;

import java.net.URL;

import models.CrowdGallery;
import models.CrowdPhoto;
import models.User;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.Invoker.Suspend;
import play.jobs.Every;
import play.jobs.Job;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import utils.TweetPhotoFactory;
import utils.TweetPhotoGrabber;

public class PhotoJob extends Job<Void>{
	private CrowdGallery crowdGallery;
	private JsonObject tweetJsonObject;
	public PhotoJob(CrowdGallery crowdGallery, JsonObject tweetJsonObject){
		this.crowdGallery = crowdGallery;
		this.tweetJsonObject = tweetJsonObject;
	}
	
	public void doJob() throws Exception {
		String tweetText = tweetJsonObject.getAsJsonPrimitive("text").getAsString();
		String username = tweetJsonObject.getAsJsonPrimitive("form_user").getAsString();
		
		StringBuffer sb = new StringBuffer(tweetText);
    	int i = sb.indexOf("http://twitpic.com");
		String sub = sb.substring(i, tweetText.length());
		String url = tweetText.substring(0, sub.indexOf(" "));
		TweetPhotoGrabber grabber = TweetPhotoFactory.create(new URL(url));
		
		CrowdPhoto photo = new CrowdPhoto();
		photo.fullImageURL = grabber.getFullImageURL();
		photo.thumbImageURL = grabber.getThumbImageURL();
		photo.posterUserName = username;
		photo.tweetContent = tweetText;
		photo.crowd = crowdGallery;
		
		photo.save();
		
		
	}
	
}
