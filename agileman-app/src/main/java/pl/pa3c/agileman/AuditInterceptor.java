package pl.pa3c.agileman;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditInterceptor extends EmptyInterceptor {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1486604751376563065L;

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		log.info("DUPA");
		return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
	}
}
