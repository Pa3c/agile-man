package pl.pa3c.agileman.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.api.team.TeamSO;
import pl.pa3c.agileman.controller.exception.ResourceAlreadyExistsException;
import pl.pa3c.agileman.controller.exception.ResourceIsInUseException;
import pl.pa3c.agileman.controller.exception.ResourceNotFoundException;
import pl.pa3c.agileman.security.SpringSecurityAuditorAware;

public abstract class CommonService<ID, T, V> {

	@Autowired
	protected ModelMapper mapper;
	protected JpaRepository<V, ID> commonRepository;
	
	@Autowired
	protected SpringSecurityAuditorAware securityAutditor;

	private Class<T> classSo;
	private Class<V> classEntity;

	@SuppressWarnings("unchecked")
	public CommonService(JpaRepository<V, ID> commonRepository) {
		this.commonRepository = commonRepository;

		Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();

		this.classSo = (Class<T>) actualTypeArguments[1];
		this.classEntity = (Class<V>) actualTypeArguments[2];
	}

	public List<T> get() {

		return commonRepository //
				.findAll().stream() //
				.map(c -> mapper.map(c, classSo)) //
				.collect(Collectors.toList());
	}

	public T get(ID id) {
		V byId = findById(id);
		return mapper.map(byId, classSo);
	}

	public T create(T entitySO) {
		try {
			final V entity = commonRepository.save(mapper.map(entitySO, classEntity));
			return mapper.map(entity, classSo);
		} catch (DataIntegrityViolationException ex) {
			throw new ResourceAlreadyExistsException();
		}
	}

	@Transactional
	public T update(ID id, T entitySO) {
		V entity = findById(id);
		mapper.map(entitySO, entity);
		return mapper.map(entity, classSo);
	}

	public V findById(ID id) {
		final int EXPECTED_SIZE = 1; // IT IS 1 BECAUSE WE EXPECT THAT EXACTLY ONE OBJECT WILL BE RETURNED
		return commonRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(new EmptyResultDataAccessException(EXPECTED_SIZE),
						String.valueOf(id)));
	}

	public void delete(ID id) {
		try {
			commonRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(e, String.valueOf(id));
		} catch (DataIntegrityViolationException e) {
			throw new ResourceIsInUseException(e);
		}
	}

}
