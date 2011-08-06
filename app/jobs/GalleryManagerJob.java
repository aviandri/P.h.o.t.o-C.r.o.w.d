package jobs;

import java.util.List;

import models.Gallery;
import models.Gallery.State;
import play.Logger;
import play.jobs.Job;

/**
 * This job responsible to get all the gallery that still need to be work on and
 * retrieve all the necessary job.
 * 
 * @author uudashr@gmail.com
 * 
 */
public class GalleryManagerJob extends Job<Void> {
    
    @Override
    public void doJob() throws Exception {
        Logger.info("Starting Gallery Manager Job...");
        List<Gallery> crowdList = Gallery.find("state != ? ORDER BY id", State.DONE).fetch();
        for (Gallery crowd : crowdList) {
            if (crowd.state != State.DONE) {
                RetrieveGalleryPhotosJob job = new RetrieveGalleryPhotosJob(crowd);
                job.now();
            }
        }
    }
    
}
