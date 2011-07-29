package utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.w3c.dom.Document;

import play.libs.WS;
import play.libs.XPath;

/**
 * This is Grabber for Twitgoo.
 * 
 * @author uudashr@gmail.com
 *
 */
public class TwitgooGrabber extends TweetPhotoGrabberAbs {
    public static final String URL_PREFIX = "http://twitgoo.com/";
    public static final String TWITGOO_INFO_URL_FORMAT = "http://twitgoo.com/api/message/info/%1s";
    
    private URL imageUrl;
    private URL thumbImageUrl;
    
    public TwitgooGrabber(URL tweetPhotoURL) {
        super(tweetPhotoURL);
    }

    @Override
    public URL getFullImageURL() {
        if (imageUrl == null) {
            grab();
        }
        return imageUrl;
    }
    
    @Override
    public URL getThumbImageURL() {
        if (thumbImageUrl == null) {
            grab();
        }
        return thumbImageUrl;
    }
    
    private void grab() {
        String twitgooUrl = tweetPhotoURL.toExternalForm();
        String twitgooId = parseId(twitgooUrl);
        Document document = WS.url(TWITGOO_INFO_URL_FORMAT, twitgooId).get().getXml();
        try {
            imageUrl = new URL(XPath.selectText("rsp/imageurl", document));
            thumbImageUrl = new URL(XPath.selectText("rsp/thumburl", document));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String parseId(String twitgooUrl) {
        if (!twitgooUrl.startsWith("http://twitgoo.com/")) {
            throw new IllegalArgumentException("Twitgoo URL should start with 'http://twitgoo.com/'");
        }
        return twitgooUrl.substring(URL_PREFIX.length());
    }
}
