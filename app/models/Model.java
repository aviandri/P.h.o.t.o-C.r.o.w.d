package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class Model extends play.db.jpa.Model {
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
