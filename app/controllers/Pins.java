package controllers;

import java.util.HashMap;
import java.util.Map;

import controllers.Secure.Security;

import models.Gallery;
import models.Pin;
import models.User;
import play.mvc.*;

@With(Secure.class)
public class Pins extends Controller {

    public static void createAjax(Long userId, Long galleryId) {
        User user = Security.connectedUser();
        notFoundIfNull(user, "You no longer exists");
        
        Gallery gallery = Gallery.findById(galleryId);
        notFoundIfNull(gallery, "Gallery doesn't exists");
        
        Pin pin = new Pin();
        pin.user = user;
        pin.gallery = gallery;        
        pin.save();
 
        Map<String, String> response = new HashMap<String, String>();
        response.put("status", "OK");
        renderJSON(response);        
    }

}
