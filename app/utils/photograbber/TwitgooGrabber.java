package utils.photograbber;

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
    
    private String imageUrl;
    private String thumbImageUrl;
    
    public TwitgooGrabber(String tweetPhotoURL) {
        super(tweetPhotoURL);
    }

    @Override
    public String getFullImageURL() {
        if (imageUrl == null) {
            grab();
        }
        return imageUrl;
    }
    
    @Override
    public String getThumbImageURL() {
        if (thumbImageUrl == null) {
            grab();
        }
        return thumbImageUrl;
    }
    
    private void grab() {
        String twitgooId = parseId(tweetPhotoURL);
        Document document = WS.url(TWITGOO_INFO_URL_FORMAT, twitgooId).get().getXml();
        imageUrl = XPath.selectText("rsp/imageurl", document);
        thumbImageUrl = XPath.selectText("rsp/thumburl", document);
    }

    public static String parseId(String twitgooUrl) {
        if (!twitgooUrl.startsWith("http://twitgoo.com/")) {
            throw new IllegalArgumentException("Twitgoo URL should start with 'http://twitgoo.com/'");
        }
        return twitgooUrl.substring(URL_PREFIX.length());
    }
}
