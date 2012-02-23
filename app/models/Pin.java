package models;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Pin extends Model {
  @ManyToOne
  @JoinColumn(name="user_id")
	public User user;
	
  @ManyToOne
  @JoinColumn(name="gallery_id")
  public Gallery gallery;
  
  public static Pin findByUserAndGallery(User user, Gallery gallery){
  	return Pin.find("user = ? AND gallery = ?", user, gallery).first();    	
  }
  
  public static List<Pin> findByUser(User user){
	return Pin.find("user = ?", user).fetch();    	
  }
	
}
