package jobs;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import models.CrowdGallery;
import play.jobs.Every;
import play.jobs.Job;
import play.libs.WS;
import play.libs.WS.HttpResponse;

public class GalleryJob extends Job<String> {
	private CrowdGallery crowdGallery;
	public GalleryJob(CrowdGallery crowdGallery){
		this.crowdGallery = crowdGallery;
	}
	
	@Override
	public void doJob() throws Exception {
		HttpResponse res = WS.url("http://search.twitter.com/search.json?q=%23"+crowdGallery.hashtag).get();
		JsonElement element = res.getJson();
		JsonArray results = element.getAsJsonObject().getAsJsonArray("results");
		for (JsonElement tweet : results) {
			JsonObject tweetObject = tweet.getAsJsonObject();
			String text = tweetObject.getAsJsonPrimitive("text").getAsString();
			StringBuffer sb = new StringBuffer(text);
			int i = sb.indexOf("http://twitpic.com");
			if(i < 0)
				continue;
			PhotoJob photoJob = new PhotoJob(crowdGallery, tweetObject);
			photoJob.run();
		}
	}
	
}
