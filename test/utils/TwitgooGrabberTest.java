package utils;

import java.net.URL;

import org.junit.Test;

import play.test.UnitTest;

/**
 * This is test for class Twitgoo
 * 
 * @author uudashr@gmail.com
 *
 */
public class TwitgooGrabberTest extends UnitTest {
    
    @Test
    public void parseId() {
        String id = TwitgooGrabber.parseId("http://twitgoo.com/2l5l7d");
        assertEquals("2l5l7d", id);
        
        id = TwitgooGrabber.parseId("http://twitgoo.com/2l5l7c");
        assertEquals("2l5l7c", id);
    }
    
    @Test
    public void urlFormat() {
        String formatted = String.format(TwitgooGrabber.TWITGOO_INFO_URL_FORMAT, "3spr5");
        assertEquals("http://twitgoo.com/api/message/info/3spr5", formatted);
    }
    
    @Test
    public void grab() throws Exception {
        new TwitgooGrabber(new URL("http://twitgoo.com/2l5l7d")).getFullImageURL();
    }
}
