package controllers.admin;

import java.util.List;

import models.Gallery;
import models.Photo;
import play.mvc.Controller;
import play.mvc.With;
import controllers.Check;
import controllers.Secure;

/**
 * @author uudashr@gmail.com
 *
 */
@With(Secure.class)
@Check("admin")
public class Galleries extends Controller {
    
    public static void index() {
        List<Gallery> galleries = Gallery.find("ORDER BY id").fetch();
        render(galleries);
    }
    
    public static void details(Long id) {
        Gallery gallery = Gallery.findById(id);
        notFoundIfNull(gallery);
        
        List<Photo> photos = Photo.find("gallery = ? ORDER BY id DESC", gallery).fetch();
        render(gallery, photos);
    }
}
