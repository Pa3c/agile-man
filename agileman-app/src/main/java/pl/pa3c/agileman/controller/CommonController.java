package pl.pa3c.agileman.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import pl.pa3c.agileman.api.BaseCrudInterface;
import pl.pa3c.agileman.service.CommonService;

public abstract class CommonController <ID, T, V> implements BaseCrudInterface<T, ID> {

	protected CommonService<ID, T, V> commonService;

	public CommonController(CommonService<ID, T, V> commonService) {
		this.commonService = commonService;
	}

	@Override
	public List<T> get() {
		return commonService.get();
	}
	
	@Override
	public T get(ID id) {
		return commonService.get(id);
	}

	@Override
	public T create(@RequestBody T entitySO) {
		return commonService.create(entitySO);
	}

	@Override
	public T update(ID id, T entitySO) {
		return commonService.update(id, entitySO);
	}
	
	@Override
	public void delete(ID id) {
		commonService.delete(id);
	}

}
