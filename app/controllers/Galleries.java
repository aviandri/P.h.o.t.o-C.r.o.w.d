package controllers;

import java.util.Date;

import models.Gallery;
import play.data.binding.As;
import play.data.validation.Match;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.With;

/**
 * @author uudashr@gmail.com
 *
 */
@With(Secure.class)
public class Galleries extends Controller {
    
    public static void create() {
        renderCreate(null);
    }
    
    private static void renderCreate(Gallery gallery) {
        render("@create", gallery);
    }
    
    public static void save(@Required(message="validation.required.gallery.name") String name, 
            @As("MM/dd/yyyy") Date startDate, 
            @As("MM/dd/yyyy") Date endDate,
            @Required(message="validation.required.gallery.hashtag") @Match(value="#?([A-Za-z0-9_]+) *", message="validation.hashtag") String hashtag, 
            String location, 
            String description) {
        
        if (endDate != null && startDate == null) {
            validation.addError("endDate", "validation.required.reference.startDate");
            endDate = null;
        }
        
        if (startDate != null && endDate != null) {
            validation.future(endDate, startDate);
        }
        
        if (hashtag != null) {
            hashtag = hashtag.trim();
        }
        
        if (validation.hasErrors()) {
            renderCreate(new Gallery(name, startDate, endDate, hashtag, location, description));
        }
        renderText("OK");
    }
}
