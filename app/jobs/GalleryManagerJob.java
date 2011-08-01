package jobs;

import java.util.List;

import models.Gallery;
import models.Gallery.State;
import play.Logger;
import play.jobs.Job;

public class GalleryManagerJob extends Job<Void> {
    @Override
    public void doJob() throws Exception {
        Logger.info("Starting Gallery Manager Job...");
        List<Gallery> crowdList = Gallery.find("state != ? ORDER BY id", State.DONE).fetch();
        for (Gallery crowd : crowdList) {
            if (crowd.state != State.DONE) {
                GalleryJob job = new GalleryJob(crowd);
                job.now();
            }
        }
    }
}
