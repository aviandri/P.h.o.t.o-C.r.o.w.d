package utils;

import java.net.URL;

public class TweetPhotoFactory {
    public static TweetPhotoGrabber create(String tweetPicURL) {
        if (tweetPicURL.contains("twitpic.com")) {
            return new TwitPicGrabber(tweetPicURL);
        } else if (tweetPicURL.contains("lockerz.com")) {
            return new LokerzGrabber(tweetPicURL);
        } else if (tweetPicURL.contains("twitgoo.com")) {
            return new TwitgooGrabber(tweetPicURL);
        } else {
            throw new RuntimeException();
        }
    }
}
