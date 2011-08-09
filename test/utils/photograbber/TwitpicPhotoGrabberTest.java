package utils.photograbber;

import org.junit.Test;

import play.test.UnitTest;
import utils.photoservice.TwitpicPhotoService;

/**
 * @author uudashr@gmail.com
 *
 */
public class TwitpicPhotoGrabberTest extends UnitTest {
    
    @Test
    public void middleParamExpires() {
        String twitpicThumbUrl = "http://s3.amazonaws.com/twitpic/photos/large/368848220.jpg?AWSAccessKeyId=AKIAJF3XCCKACR3QDMOA&Expires=1312926252&Signature=xwC90ofLaWfFVpoHmX0b4AuSmsI%3D";
        assertEquals("1312926252", TwitpicPhotoService.extractExpires(twitpicThumbUrl));
    }
    
    @Test
    public void firstParamExpires() {
        String twitpicThumbUrl = "http://s3.amazonaws.com/twitpic/photos/large/368848220.jpg?Expires=1312926252&AWSAccessKeyId=AKIAJF3XCCKACR3QDMOA&Signature=xwC90ofLaWfFVpoHmX0b4AuSmsI%3D";
        assertEquals("1312926252", TwitpicPhotoService.extractExpires(twitpicThumbUrl));
    }
    
    @Test
    public void lastParamExpires() {
        String twitpicThumbUrl = "http://s3.amazonaws.com/twitpic/photos/large/368848220.jpg?AWSAccessKeyId=AKIAJF3XCCKACR3QDMOA&Signature=xwC90ofLaWfFVpoHmX0b4AuSmsI%3D&Expires=1312926252";
        assertEquals("1312926252", TwitpicPhotoService.extractExpires(twitpicThumbUrl));
    }
    
}
