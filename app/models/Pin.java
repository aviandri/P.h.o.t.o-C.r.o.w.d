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
	
}
