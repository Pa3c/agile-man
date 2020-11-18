package pl.pa3c.agileman.model.base;

public interface IdEntity<T> {
	void setId(T id);
	T getId();
}
