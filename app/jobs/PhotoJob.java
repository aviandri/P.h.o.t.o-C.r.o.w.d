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
	private String url;
	private String username;
	private String tweetText;
	public PhotoJob(CrowdGallery crowdGallery, String url, String username, String tweetText){
		this.crowdGallery = crowdGallery;
		this.url = url;
		this.username = username;
		this.tweetText = tweetText;
	}
	
	public void doJob() throws Exception {
		TweetPhotoGrabber grabber = TweetPhotoFactory.create(new URL(url));
		CrowdPhoto photo = new CrowdPhoto();
		photo.fullImageURL = grabber.getFullImageURL();
		photo.thumbImageURL = grabber.getThumbImageURL();
		photo.posterUserName = username;
		photo.tweetContent = tweetText;
		photo.crowd = crowdGallery;
		
		photo.save();
		CrowdGallery crowd = CrowdGallery.findById(crowdGallery.id);
		crowd.crowdPhotos.add(photo);
		crowd.save();
		
	}
	
}
