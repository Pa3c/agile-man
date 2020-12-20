package pl.pa3c.agileman.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.label.LabelSO;
import pl.pa3c.agileman.model.label.Label;
import pl.pa3c.agileman.model.label.Type;
import pl.pa3c.agileman.repository.LabelRepository;

@Service
public class LabelService extends CommonService<String, LabelSO, Label> {

	@Autowired
	public LabelService(JpaRepository<Label, String> labelRepository) {
		super(labelRepository);
	}

	public List<LabelSO> getFiltered(String type, String value) {

		return ((LabelRepository) repository).findByTypeAndIdContainingIgnoreCase(Type.valueOf(type), value).stream()
				.map(x -> mapper.map(x, LabelSO.class)).collect(Collectors.toList());
	}

}
