package pl.pa3c.agileman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.label.LabelSO;
import pl.pa3c.agileman.model.label.Label;

@Service
public class LabelService extends CommonService<String, LabelSO, Label>{


	@Autowired
	public LabelService(JpaRepository<Label, String> labelRepository) {
		super(labelRepository);
	}

}
