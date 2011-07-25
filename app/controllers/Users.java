package controllers;

import controllers.Secure.Security;
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
        render("@galleries", user);
    }
    
    public static void galleries(String username) {
        User user = User.findByUsername(username);
        notFoundIfNull(user);
        
        render(user);
    }
}
