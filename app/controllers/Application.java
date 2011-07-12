package controllers;

import play.*;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.XPath;
import play.mvc.*;
import utils.TweetPhotoFactory;
import utils.TweetPhotoGrabber;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import jobs.GalleryManagerJob;
import jobs.PhotoJob;

import models.CrowdGallery;
import models.User;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class Application extends Controller {

    public static void index() {
//    	HttpResponse res = WS.url("http://www.google.com").get();
//    	System.out.println(res.toString());
    	render();
    }
    
    public static void show() throws IOException{
    	TweetPhotoGrabber grabber = TweetPhotoFactory.create(new URL("http://twitpic.com/2giosz"));
    	System.out.println(grabber.getFullImageURL());
    	render();
    }
    
    public static void test() throws IOException{
    	GalleryManagerJob job = new GalleryManagerJob();
    	job.run();
    }
}