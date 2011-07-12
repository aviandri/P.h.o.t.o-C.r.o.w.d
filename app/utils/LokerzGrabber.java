package utils;

import java.net.MalformedURLException;
import java.net.URL;

import play.libs.WS;
import play.libs.WS.HttpResponse;

public class LokerzGrabber extends TweetPhotoGrabberAbs {
	private URL fullImageURL;
	private URL thumbImageURL;
	public LokerzGrabber(URL tweetPhotoURL) {
		super(tweetPhotoURL);
	}

	@Override
	public URL getFullImageURL() {
		try {
			this.fullImageURL = new URL("http://api.plixi.com/api/tpapi.svc/imagefromurl?url="+tweetPhotoURL.toExternalForm()+"&size=large");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return this.fullImageURL;
	}

	@Override
	public URL getThumbImageURL() {
		try {
			this.thumbImageURL = new URL("http://api.plixi.com/api/tpapi.svc/imagefromurl?url="+tweetPhotoURL.toExternalForm()+"&size=small");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return this.thumbImageURL;
	}

	
}
