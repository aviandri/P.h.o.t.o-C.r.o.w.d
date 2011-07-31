package utils;

import java.io.IOException;
import java.net.URL;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import play.Logger;
import play.libs.WS;
import play.libs.WS.HttpResponse;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class TwitPicGrabber extends TweetPhotoGrabberAbs {
    public String fullImageURL;
    public String thumbImageURL;

    public TwitPicGrabber(String tweetPhotoURL) {
        super(tweetPhotoURL);
    }

    @Override
    public String getFullImageURL() {
        if (fullImageURL == null) {
            grab();
        }
        return fullImageURL;
    }

    @Override
    public String getThumbImageURL() {
        if (thumbImageURL == null) {
            grab();
        }
        return thumbImageURL;
    }

    protected void grab() {
        HttpResponse res = WS.url(tweetPhotoURL).get();
        StringBuffer html = new StringBuffer(res.getString());
        Source source;
        try {
            source = new Source(new ByteInputStream(html.toString().getBytes(),
                    html.toString().length()));
            Element el = source.getElementById("photo-display");
            String url = el.getAttributeValue("src");
            
            fullImageURL = url;
            thumbImageURL = url;
        } catch (IOException e) {
            Logger.error("Failed getting photo from twitpic using URL %1s", tweetPhotoURL);
        }

    }

}
