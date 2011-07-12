package models;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class CrowdGallery extends BaseModel {
    @Required
	public String hashtag;
    @Required
    public User creator;
    @Column(name="last_id")
    public int lastId;
    @Column()
    public boolean state=true;
}
