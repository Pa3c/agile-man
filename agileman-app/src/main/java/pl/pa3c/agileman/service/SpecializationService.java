package pl.pa3c.agileman.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.IdSO;
import pl.pa3c.agileman.api.user.UserSpecializationSO;
import pl.pa3c.agileman.controller.exception.ResourceAlreadyExistsException;
import pl.pa3c.agileman.model.user.Specialization;
import pl.pa3c.agileman.model.user.UserSpecialization;
import pl.pa3c.agileman.repository.SpecializationRepository;
import pl.pa3c.agileman.repository.UserSpecializationRepository;
import pl.pa3c.agileman.repository.user.UserRepository;

@Service
public class SpecializationService extends CommonService<String, UserSpecializationSO, Specialization> {

	@Autowired
	private UserSpecializationRepository userSpecializationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public SpecializationService(JpaRepository<Specialization, String> userRepository) {
		super(userRepository);
	}

	@Transactional
	public UserSpecializationSO addUserSpecialization(String name, String login, UserSpecializationSO specialization) {

		final Optional<Specialization> spec = ((SpecializationRepository) repository).findByIdIgnoreCase(name)
				.or(() -> {
					super.create(specialization);
					return Optional.of(repository.getOne(name));
				});
		final UserSpecialization userSpecialization = new UserSpecialization();
		userSpecialization.setSpecialization(spec.get());
		userSpecialization.setUser(userRepository.getOne(login));
		userSpecialization.setSkill(specialization.getSkill());
		try {
			userSpecializationRepository.save(userSpecialization);			
		} catch (DataIntegrityViolationException ex) {
			throw new ResourceAlreadyExistsException();
		}
		return returnWithSkill(name, specialization.getSkill());
	}

	@Transactional
	public UserSpecializationSO updateUserSpecialization(String name, String login,
			UserSpecializationSO specialization) {

		userSpecializationRepository.findBySpecializationIdAndUserId(name, login).setSkill(specialization.getSkill());

		return returnWithSkill(name, specialization.getSkill());
	}

	public UserSpecializationSO returnWithSkill(String name, Integer skill) {
		final UserSpecializationSO spec = super.get(name);
		spec.setSkill(skill);
		return spec;
	}

	@Transactional
	public void deleteUserSpecialization(String name, String login) {
		userSpecializationRepository.deleteBySpecializationIdAndUserId(name, login);
	}

	public List<IdSO<String>> filterSpecializations(String name) {
		final List<Specialization> spec = ((SpecializationRepository) repository).findByIdContainingIgnoreCase(name);
		return spec.stream().map(x -> new IdSO<>(x.getId())).collect(Collectors.toList());
	}

}
