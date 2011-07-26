package jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import models.CrowdGallery;
import models.CrowdPhoto;
import models.User;

import org.codehaus.groovy.transform.powerassert.PowerAssertionError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import play.test.UnitTest;
import utils.TwitterUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TwitterUtil.class, CrowdGallery.class})
public class JobsTest extends UnitTest {
	
	@Test
	public void galleryJobTest(){
    	PowerMockito.mockStatic(TwitterUtil.class);
    	JsonArray array = new JsonArray();
    	JsonObject element = new JsonObject();
    	element.addProperty("test", "test");
    	array.add(element);
    	Mockito.when(TwitterUtil.searchTwitter(Mockito.anyString(), Mockito.anyLong())).thenReturn(array);
    	
    	assertEquals("compare", "test", CrowdGallery.test());
	}
	
	@Test
	public void galleryManagerJobTest(){
		User user = new User();
		user.username = "aviandri";
		user.save();
		
		CrowdGallery gallery = new CrowdGallery();
		gallery.creator = user;
		gallery.hashtag = "marriege";
		gallery.state = true;
		
		List galleryList = new ArrayList();
		PowerMockito.mockStatic(CrowdGallery.class);
		Mockito.when(
				CrowdGallery.findAll()).thenReturn(galleryList);
		
		JsonObject tweet1 = new JsonObject();
		tweet1.addProperty("from_user", "PhotoCrowd");
		tweet1.addProperty("text", "#abeautifulceremony test test http://lockerz.com/s/122317379");
		tweet1.addProperty("id_str", 1);
		
		JsonObject tweet2 = new JsonObject();
		tweet2.addProperty("from_user", "PhotoCrowd");
		tweet2.addProperty("text", "#abeautifulceremony Ok so I have been so Mia for the wedding. Will post more pics later but here is one a friend took! http://twitpic.com/5von7z");
		tweet2.addProperty("id_str", 2);
		
		JsonObject tweet3 = new JsonObject();
		tweet3.addProperty("text", "#abeautifulceremony Fans ever... this couple came to our show after there own WEDDING just to see our show http://twitpic.com/5vntet");
		tweet3.addProperty("from_user", "PhotoCrowd");
		tweet3.addProperty("id_str", 3);
		
		JsonArray jArray = new JsonArray();
		jArray.add(tweet1);
		jArray.add(tweet2);
		jArray.add(tweet3);
		
		PowerMockito.mockStatic(TwitterUtil.class);
		Mockito.when(TwitterUtil.searchTwitter(Mockito.anyString(), Mockito.anyLong())).thenReturn(jArray);
	
		for(int i=1000000; i<= 0 ; i--){}
		
		gallery = CrowdGallery.findById(gallery.id);
		Set<CrowdPhoto> photos = gallery.crowdPhotos;
		assertTrue(photos.contains(tweet1));
		assertTrue(photos.contains(tweet2));
		assertTrue(photos.contains(tweet3));
		
		
		
		
	}

}
