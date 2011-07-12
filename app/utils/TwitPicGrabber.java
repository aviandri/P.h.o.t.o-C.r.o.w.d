package utils;

import java.io.IOException;
import java.net.URL;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import play.libs.WS;
import play.libs.WS.HttpResponse;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class TwitPicGrabber extends TweetPhotoGrabberAbs {
	public URL fullImageURL;
	public URL thumbImageURL;
	
	public TwitPicGrabber(URL tweetPhotoURL) {
		super(tweetPhotoURL);
	}

	@Override
	public URL getFullImageURL() {
		if(this.fullImageURL == null)
			grab();
		return this.fullImageURL;
	}

	@Override
	public URL getThumbImageURL() {
		if(this.thumbImageURL == null)
			grab();
		return this.thumbImageURL;
	}

	protected void grab() {
		HttpResponse res = WS.url(tweetPhotoURL.toExternalForm()).get();
    	StringBuffer html = new StringBuffer(res.getString());
    	Source source;
		try {
			source = new Source(new ByteInputStream(html.toString().getBytes(),html.toString().length()));
			Element el  =source.getElementById("photo-display");
	    	String url = el.getAttributeValue("src");
	    	System.out.println(url);
	    	this.fullImageURL = new URL(url);
	    	this.thumbImageURL = new URL(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
	}
	
	

}
