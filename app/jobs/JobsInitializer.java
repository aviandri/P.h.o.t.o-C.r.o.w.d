package jobs;

import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class JobsInitializer extends Job<Void> {

    @Override
    public void doJob() throws Exception {
        initGalleryMangerJob();
    }

    private void initGalleryMangerJob() {
        GalleryManagerJob galleryManagerJob = new GalleryManagerJob();
        galleryManagerJob.every(30);
        galleryManagerJob.now();
    }

}