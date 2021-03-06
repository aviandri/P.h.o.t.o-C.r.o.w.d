import java.util.List;

import models.User;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.Crypto;
import play.test.Fixtures;

@OnApplicationStart
public class LoadInitialData extends Job<Boolean>{
	
	public void doJob() throws Exception {
		Logger.info("Initializing data...");
        Fixtures.load("initial-data.yml");
	}
}
