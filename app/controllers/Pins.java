package controllers;

import java.util.HashMap;
import java.util.Map;

import models.Gallery;
import models.Pin;
import models.User;
import play.Logger;
import play.mvc.*;

public class Pins extends Controller {

    public static void createAjax(Long userId, Long galleryId) {
        Pin pin = new Pin();
        User user = User.findById(userId);
        Gallery gallery = Gallery.findById(galleryId);
        pin.user = user;
        pin.gallery = gallery;        
        pin.save();
        Map<String, String> response = new HashMap<String, String>();
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
