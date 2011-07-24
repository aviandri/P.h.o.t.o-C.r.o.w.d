package controllers;

import play.mvc.Controller;
import play.mvc.With;

/**
 * @author uudashr@gmail.com
 *
 */
@With(value=Secure.class)
public class Users extends Controller {
    public static void galleries() {
        render();
    }
}
