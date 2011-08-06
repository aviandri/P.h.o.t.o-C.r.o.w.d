package utils.photograbber;

import org.junit.Test;

import play.test.UnitTest;
import utils.photoservice.TwitgooPhotoService;

/**
 * This is test for class Twitgoo
 * 
 * @author uudashr@gmail.com
 *
 */
public class TwitgooGrabberTest extends UnitTest {
    
    @Test
    public void parseId() {
        String id = TwitgooPhotoService.parseId("http://twitgoo.com/2l5l7d");
        assertEquals("2l5l7d", id);
        
        id = TwitgooPhotoService.parseId("http://twitgoo.com/2l5l7c");
        assertEquals("2l5l7c", id);
    }
    
    @Test
    public void urlFormat() {
        String formatted = String.format(TwitgooPhotoService.TWITGOO_INFO_URL_FORMAT, "3spr5");
        assertEquals("http://twitgoo.com/api/message/info/3spr5", formatted);
    }
    
    @Test
    public void grab() throws Exception {
        new TwitgooPhotoService().grab("http://twitgoo.com/2l5l7d");
    }
}
