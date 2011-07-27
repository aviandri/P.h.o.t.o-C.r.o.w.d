package utils;

import java.net.URL;

public abstract class TweetPhotoGrabberAbs implements TweetPhotoGrabber {
    URL tweetPhotoURL;

    public TweetPhotoGrabberAbs(URL tweetPhotoURL) {
        this.tweetPhotoURL = tweetPhotoURL;
    }

}
