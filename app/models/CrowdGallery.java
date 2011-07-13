package models;

import play.*;
import play.data.validation.Required;

import javax.persistence.*;
import java.util.*;

@Entity
public class CrowdGallery extends Model {
    @Required
	public String hashtag;
    @Required
    public User creator;
    @Column(name="last_id")
    public int lastId;
    public boolean state=true;
}
