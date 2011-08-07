package utils.photoservice;

import java.io.IOException;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import play.Logger;
import play.libs.WS;
import play.libs.WS.HttpResponse;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

/**
 * Photo service implementation of Twitpic.
 * 
 * @author uudashr@gmail.com
 *
 */
public class TwitpicPhotoService extends AbstractPhotoService {
    
    public TwitpicPhotoService() {
        super("twitpic.com", "http://twitpic.com/");
    }

    @Override
    public ImageAndThumbnailUrlHolder grab(String photoUrl) {
        HttpResponse res = WS.url(photoUrl).get();
        StringBuffer html = new StringBuffer(res.getString());
        Source source;
        try {
            source = new Source(new ByteInputStream(html.toString().getBytes(),
                    html.toString().length()));
            Element el = source.getElementById("photo-display");
            if (el == null) {
                Logger.warn("Cannot find element photo-display from URL %1s", photoUrl);
                return null;
            }
            String url = el.getAttributeValue("src");
            
            return new ImageAndThumbnailUrlHolder(url, url);
        } catch (IOException e) {
            Logger.error(e, "Failed getting photo from twitpic using URL %1s", photoUrl);
        }
        return null;
    }
}
