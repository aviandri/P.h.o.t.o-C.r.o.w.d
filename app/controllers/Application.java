package controllers;

import play.mvc.Controller;
import controllers.Secure.Security;


public class Application extends Controller {

    public static void index() {
		if(Security.isConnected()){
			Galleries.index();
		}
    	render();
    }
    
    public static void show(){
    	render();
    }
}
