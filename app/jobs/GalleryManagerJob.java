package jobs;

import java.util.List;

import models.Gallery;
import play.Logger;
import play.jobs.Every;
import play.jobs.Job;

public class GalleryManagerJob extends Job<Void> {
    @Override
    public void doJob() throws Exception {
        Logger.info("Starting Gallery Manager Job...");
        List<Gallery> crowdList = Gallery.findAll();
        for (Gallery crowd : crowdList) {
            if (crowd.state) {
                GalleryJob job = new GalleryJob(crowd);
                job.now();
            }
        }
    }
}
