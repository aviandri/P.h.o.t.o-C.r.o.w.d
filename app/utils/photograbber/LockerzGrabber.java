package utils.photograbber;

import java.net.MalformedURLException;
import java.net.URL;

public class LockerzGrabber extends TweetPhotoGrabberAbs {
    private String fullImageURL;
    private String thumbImageURL;

    public LockerzGrabber(String tweetPhotoURL) {
        super(tweetPhotoURL);
        fullImageURL = "http://api.plixi.com/api/tpapi.svc/imagefromurl?url="
                + tweetPhotoURL + "&size=large";
        thumbImageURL = "http://api.plixi.com/api/tpapi.svc/imagefromurl?url="
                + tweetPhotoURL + "&size=small";
    }

    @Override
    public String getFullImageURL() {
        return fullImageURL;
    }

    @Override
    public String getThumbImageURL() {
        return thumbImageURL;
    }

}
