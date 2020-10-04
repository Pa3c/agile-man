package pl.pa3c.agileman.model;

import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class IdEntity<T> {
	protected T id;
}
