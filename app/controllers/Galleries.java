package controllers;

import java.util.Date;
import java.util.List;

import models.Gallery;
import models.Photo;
import play.data.binding.As;
import play.data.validation.Match;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.With;
import controllers.Secure.Security;

/**
 * @author uudashr@gmail.com
 * 
 */
@With(Secure.class)
public class Galleries extends Controller {
    
    public static void index() {
        List<Gallery> galleries =  Gallery.findAll();
        render(galleries);
    }
    
    public static void create() {
        renderCreate(null);
    }

    private static void renderCreate(Gallery gallery) {
        render("@create", gallery);
    }

    public static void save(
            @Required(message = "validation.required.gallery.name") String name,
            @As("MM/dd/yyyy") Date startDate,
            @As("MM/dd/yyyy") Date endDate,
            @Required(message = "validation.required.gallery.hashtag") 
                @Match(value = "#?[A-Za-z0-9_]+ *", 
                message = "validation.hashtag") 
                String hashtag,
            String location, String description) {

        if (endDate != null && startDate == null) {
            validation.addError("endDate",
                    "validation.required.reference.startDate");
            endDate = null;
        }

        if (startDate != null && endDate != null) {
            validation.future(endDate, startDate);
        }

        hashtag = cleanHashtag(hashtag);

        Gallery gallery = new Gallery(name, startDate, endDate, hashtag,
                location, description);
        if (validation.hasErrors()) {
            renderCreate(gallery);
        }

        gallery.user = Security.connectedUser();
        gallery.save();
        
        Users.myGalleries();
    }
    
    private static String cleanHashtag(String hashtag) {
        if (hashtag.startsWith("#")) {
            return hashtag.substring(1).trim();
        }
        return hashtag.trim();
    }
    
    public static void details(Long galleryId) {
        Gallery gallery = Gallery.findById(galleryId);
        List<Photo> photos = Photo.find("gallery = ? ORDER BY id DESC", gallery).fetch();
        render(gallery, photos);
    }
}
