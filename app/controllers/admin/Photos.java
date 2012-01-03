package controllers.admin;

import controllers.Secure;
import models.Photo;
import play.mvc.Controller;
import play.mvc.With;

/**
 * @author uudashr@gmail.com
 *
 */
@With(Secure.class)
public class Photos extends Controller {
    
    public static void revalidate(Long id) {
        Photo photo = Photo.findByIdAndRevalidate(id);
        notFoundIfNull(photo);
        
        Galleries.details(photo.gallery.id);
    }
}
