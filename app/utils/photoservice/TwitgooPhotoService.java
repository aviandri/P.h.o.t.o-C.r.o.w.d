package utils.photoservice;

import org.w3c.dom.Document;

import play.Logger;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.XPath;

/**
 * Photo service implementation fot Twitgoo.
 * 
 * @author uudashr@gmail.com
 *
 */
public class TwitgooPhotoService extends AbstractPhotoService {
    public static final String URL_PREFIX = "http://twitgoo.com/";
    public static final String TWITGOO_INFO_URL_FORMAT = "http://twitgoo.com/api/message/info/%s";
    
    public TwitgooPhotoService() {
        super("twitgoo.com", "http://twitgoo.com");
    }

    @Override
    public ImageAndThumbnailUrlHolder grab(String photoUrl) {
        String twitgooId = parseId(photoUrl);
        HttpResponse resp = WS.url(TWITGOO_INFO_URL_FORMAT, twitgooId).get();
        if (resp.getStatus() != 200) {
            Logger.error("Failed getting response from twitgoo for %1s", photoUrl);
            return null;
        }
        Document document = resp.getXml();
        String url = XPath.selectText("rsp/imageurl", document);
        String thumbUrl = XPath.selectText("rsp/thumburl", document);
        return new ImageAndThumbnailUrlHolder(url, thumbUrl);
    }
    
    public static String parseId(String twitgooUrl) {
        if (!twitgooUrl.startsWith(URL_PREFIX)) {
            throw new IllegalArgumentException("Twitgoo URL should start with 'http://twitgoo.com/'");
        }
        return twitgooUrl.substring(URL_PREFIX.length());
    }
}
