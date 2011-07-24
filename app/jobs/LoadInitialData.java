package jobs;
import java.util.List;

import models.User;
import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.Crypto;
import play.test.Fixtures;

//@OnApplicationStart(async)
public class LoadInitialData extends Job<Boolean>{
	
	public void doJob() throws Exception {
		Logger.info("Initializing data...");
		if (Play.mode.isDev() && User.count() == 0) {
		    Fixtures.loadModels("initial-data.yml");
		}
	}
}
