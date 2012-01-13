package utils.photoservice;

import java.io.IOException;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import play.Logger;
import play.libs.WS;
import play.libs.WS.HttpResponse;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class TwitterPhotoService extends AbstractPhotoService {

	public TwitterPhotoService() {
		super("pic.twitter.com", "http://p.twimg.com");
	}
	@Override
	public ImageAndThumbnailUrlHolder grab(String photoUrl) {
		String fullImageUrl = photoUrl+":large";
		String thumbImageUrl = photoUrl+":small";
		return new ImageAndThumbnailUrlHolder(photoUrl, fullImageUrl, thumbImageUrl);
	}
}
