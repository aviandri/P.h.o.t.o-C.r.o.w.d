package utils;

import java.net.URL;

public class TweetPhotoFactory {
	public static TweetPhotoGrabber create(URL tweetPicURL ){
		String path = tweetPicURL.toExternalForm();
		if(path.contains("twitpic.com")){
			return new TwitPicGrabber(tweetPicURL);
		}
		else{
			throw new RuntimeException();
		}
	}
}
