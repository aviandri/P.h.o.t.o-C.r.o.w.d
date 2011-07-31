package utils.photograbber;

import java.net.URL;

public abstract class TweetPhotoGrabberAbs implements TweetPhotoGrabber {
    protected String tweetPhotoURL;

    public TweetPhotoGrabberAbs(String tweetPhotoURL) {
        this.tweetPhotoURL = tweetPhotoURL;
    }

}
