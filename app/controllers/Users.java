package controllers;

import java.util.List;

import controllers.Secure.Security;
import models.Gallery;
import models.User;
import play.mvc.Controller;
import play.mvc.With;

/**
 * @author uudashr@gmail.com
 *
 */
@With(value=Secure.class)
public class Users extends Controller {
    
    public static void myGalleries() {
        User user  = Security.connectedUser();
        List<Gallery> galleries = Gallery.find("user = ? ORDER BY dateCreated DESC", user).fetch();
        render("@galleries", user, galleries);
    }
    
    public static void galleries(String username) {
        User user = User.findByUsername(username);
        notFoundIfNull(user);
        
        List<Gallery> galleries = Gallery.find("user = ? ORDER BY dateCreated DESC", user).fetch();
        render(user, galleries);
    }
}
