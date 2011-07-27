package controllers;

import play.*;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.mvc.Controller;


public class Application extends Controller {

    public static void index() {
    	String userId = session.get("session.userid");
		if(userId!=null){
			Galleries.index();
		}
    	render();
    }
    
}