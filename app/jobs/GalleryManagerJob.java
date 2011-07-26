package jobs;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import models.CrowdGallery;
import models.User;
import play.Logger;
import play.jobs.Every;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.WS;
import play.libs.WS.HttpResponse;

@Every("10s")
public class GalleryManagerJob extends Job<Void> {
	@Override
	public void doJob() throws Exception {
		Logger.info("job is started");
		System.out.println("job is started");
		List<CrowdGallery> crowdList = CrowdGallery.findAll();
		for (CrowdGallery crowd : crowdList) {
			if(crowd.state){
				GalleryJob job = new GalleryJob(crowd);
				job.now();
			}
		}
	}
}
