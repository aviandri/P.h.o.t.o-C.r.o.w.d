package controllers;

import java.util.HashMap;
import java.util.Map;

import controllers.Secure.Security;

import models.Gallery;
import models.Pin;
import models.User;
import play.Logger;
import play.mvc.*;

@With(Secure.class)
public class Pins extends Controller {

    public static void createAjax(Long galleryId) {
    	Map<String, String> response = new HashMap<String, String>();
    	User user = Security.connectedUser();
        notFoundIfNull(user, "You no longer exists");
        
        Gallery gallery = Gallery.findById(galleryId);
        notFoundIfNull(gallery, "Gallery doesn't exists");
        
        if(Pin.findByUserAndGallery(user, gallery) != null){
        	response.put("status", "ERROR");
        	response.put("description", "user cannot pin the same gallery multiple times");
        	renderJSON(response);
        }
        Pin pin = new Pin();
        pin.user = user;
        pin.gallery = gallery;        
        pin.save();
        response.put("status", "OK");
        renderJSON(response);        
    }
    
    public static void destroyAjax(Long pinId) {
    	Pin pin = Pin.findById(pinId);
    	Map<String, String> response = new HashMap<String, String>();
    	if(pin == null){
    		response.put("status", "error");
    		response.put("message", "cannot found pin with id:"+pinId);
    		renderJSON(response);
    	}
    	pin.delete();
        response.put("status", "OK");
        renderJSON(response);        
    }
    
}
