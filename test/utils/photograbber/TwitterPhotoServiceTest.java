package utils.photograbber;

import org.junit.Test;

import play.test.UnitTest;
import utils.photoservice.ImageAndThumbnailUrlHolder;
import utils.photoservice.TwitterPhotoService;

public class TwitterPhotoServiceTest extends UnitTest{
	
	@Test
	public void testGrab(){
		TwitterPhotoService photoService = new TwitterPhotoService();
		ImageAndThumbnailUrlHolder holder = photoService.grab("http://twitter.com/PhotoCrowd/status/157348360695726081/photo/1");
		System.out.println("****************");
		System.out.println("****************");
		System.out.println("****************");
		System.out.println(holder.url);
		System.out.println("****************");
		System.out.println("****************");
		System.out.println("****************");
	}

}
