package jobs;

import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class InitializeJobs extends Job<Void> {

    @Override
    public void doJob() throws Exception {
        super.doJob();
        initGalleryMangerJob();

    }

    private void initGalleryMangerJob() {
        GalleryManagerJob galleryManagerJob = new GalleryManagerJob();
        galleryManagerJob.every(30);
        galleryManagerJob.now();
    }

}