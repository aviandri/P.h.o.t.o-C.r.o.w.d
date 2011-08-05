import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

import play.test.UnitTest;

public class BasicTest extends UnitTest {

    @Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }
    
    @Test
    public void dateTest() throws Exception {
        String twitterDate = "Wed, 03 Aug 2011 18:24:47 +0000";
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        dateFormat.setLenient(false);
        Date date = dateFormat.parse(twitterDate);
        
        assertEquals(twitterDate, dateFormat.format(date));
    }

}
