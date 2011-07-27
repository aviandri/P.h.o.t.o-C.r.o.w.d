package jobs;

import java.util.List;

import models.Gallery;
import play.Logger;
import play.jobs.Every;
import play.jobs.Job;

//@Every("10s") // disabled since we don't have the Gallery and Photo schema
public class GalleryManagerJob extends Job<Void> {
    @Override
    public void doJob() throws Exception {
        Logger.info("job is started");
        List<Gallery> crowdList = Gallery.findAll();
        for (Gallery crowd : crowdList) {
            if (crowd.state) {
                GalleryJob job = new GalleryJob(crowd);
                job.now();
            }
        }
    }
}
