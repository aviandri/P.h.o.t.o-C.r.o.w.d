package utils;

import java.net.URL;

public class TweetPhotoFactory {
    public static TweetPhotoGrabber create(URL tweetPicURL) {
        String path = tweetPicURL.toExternalForm();
        if (path.contains("twitpic.com")) {
            return new TwitPicGrabber(tweetPicURL);
        } else if (path.contains("lockerz.com")) {
            return new LokerzGrabber(tweetPicURL);
        } else if (path.contains("twitgoo.com")) {
            return new TwitgooGrabber(tweetPicURL);
        } else {
            throw new RuntimeException();
        }
    }
}
