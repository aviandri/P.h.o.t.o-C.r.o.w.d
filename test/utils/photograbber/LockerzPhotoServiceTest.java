package utils.photograbber;

import org.junit.Test;

import play.test.UnitTest;
import utils.photoservice.LockerzPhotoService;

/**
 * @author uudashr@gmail.com
 *
 */
public class LockerzPhotoServiceTest extends UnitTest {
    
    @Test
    public void find() {
        LockerzPhotoService photoService = new LockerzPhotoService();
        String[] results = photoService.findURL("this is a tweet http://lockerz.com/s/asdfg ok");
        assertEquals("http://lockerz.com/s/asdfg", results[0]);
    }
    
    public void findGotTwo() {
        LockerzPhotoService photoService = new LockerzPhotoService();
        String[] results = photoService.findURL("this is a tweet http://lockerz.com/s/asdfg ok http://lockerz.com/s/asdafds aa");
        assertEquals("http://lockerz.com/s/asdfg", results[0]);
        assertEquals("http://lockerz.com/s/asdafds", results[1]);
    }
    
}
