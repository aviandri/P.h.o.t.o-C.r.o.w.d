package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class BaseModel extends Model {
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date dateUpdated;

	@SuppressWarnings("unused")
	@PrePersist
	private void prePersist() {
		dateCreated = new Date();
		dateUpdated = new Date();
	}

	@SuppressWarnings("unused")
	@PreUpdate
	private void preUpdate() {
		dateUpdated = new Date();
	}
}
