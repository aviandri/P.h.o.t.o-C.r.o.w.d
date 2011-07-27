package jobs;

import java.util.List;

import models.CrowdGallery;
import play.Logger;
import play.jobs.Every;
import play.jobs.Job;

@Every("10s")
public class GalleryManagerJob extends Job<Void> {
    @Override
    public void doJob() throws Exception {
        Logger.info("job is started");
        List<CrowdGallery> crowdList = CrowdGallery.findAll();
        for (CrowdGallery crowd : crowdList) {
            if (crowd.state) {
                GalleryJob job = new GalleryJob(crowd);
                job.now();
            }
        }
    }
}
