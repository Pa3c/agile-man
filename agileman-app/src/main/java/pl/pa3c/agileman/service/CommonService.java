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

import lombok.extern.slf4j.Slf4j;
import pl.pa3c.agileman.controller.exception.ResourceAlreadyExistsException;
import pl.pa3c.agileman.controller.exception.ResourceIsInUseException;
import pl.pa3c.agileman.controller.exception.ResourceNotFoundException;
import pl.pa3c.agileman.security.SpringSecurityAuditorAware;

@Slf4j
public abstract class CommonService<ID, T, V> {

	@Autowired
	protected ModelMapper mapper;
	protected JpaRepository<V, ID> repository;

	@Autowired
	protected SpringSecurityAuditorAware securityAutditor;

	protected Class<T> classSo;
	protected Class<V> classEntity;

	@SuppressWarnings("unchecked")
	public CommonService(JpaRepository<V, ID> commonRepository) {
		this.repository = commonRepository;

		Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();

		this.classSo = (Class<T>) actualTypeArguments[1];
		this.classEntity = (Class<V>) actualTypeArguments[2];
	}

	public List<T> get() {

		return repository //
				.findAll().stream() //
				.map(c -> mapper.map(c, classSo)) //
				.collect(Collectors.toList());
	}

	public T get(ID id) {
		V byId = findById(id);
		return mapper.map(byId, classSo);
	}

	@Transactional
	public T create(T entitySO) {
		final V en = mapper.map(entitySO, classEntity);
		final V entity = save(en);
		return mapper.map(entity, classSo);
	}

	protected V save(V entity) {
		try {
			return repository.save(entity);
		} catch (DataIntegrityViolationException ex) {
			throw new ResourceAlreadyExistsException();
		}
	}

	@Transactional
	public T update(ID id, T entitySO) {
		V entity = findById(id);
		mapper.map(entitySO, entity);
		final T returnedEntity = mapper.map(entity, classSo);
		return returnedEntity;
	}

	public V findById(ID id) {
		final int EXPECTED_SIZE = 1; // IT IS 1 BECAUSE WE EXPECT THAT EXACTLY ONE OBJECT WILL BE RETURNED
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(new EmptyResultDataAccessException(EXPECTED_SIZE),
						String.valueOf(id)));
	}

	public void delete(ID id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(e, String.valueOf(id));
		} catch (DataIntegrityViolationException e) {
			throw new ResourceIsInUseException(e);
		}
	}

}
