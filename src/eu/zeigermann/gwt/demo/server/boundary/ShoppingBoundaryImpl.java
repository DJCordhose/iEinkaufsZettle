package eu.zeigermann.gwt.demo.server.boundary;

import java.util.List;

import javax.inject.Inject;

import eu.zeigermann.gwt.demo.server.service.ShoppingListService;
import eu.zeigermann.gwt.demo.server.service.WrapDetachService;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundary;
import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

@SuppressWarnings("serial")
public class ShoppingBoundaryImpl extends AbstractSpringBoundary
		implements ShoppingBoundary {

	@Inject
	ShoppingListService listService;

	@Inject
	WrapDetachService wrapDetachService;
	
	@Override
	public ShoppingList createList(String name) {
		ShoppingList list = listService.create();
		list.setName(name);
		list = listService.save(list);
		ShoppingList detached = wrapDetachService.detach(list);
		return detached;
	}
	
	@Override
	public List<ShoppingList> getAllLists() {
		List<ShoppingList> all = listService.getAll();
		List<ShoppingList> detached = wrapDetachService.detach(all);
		return detached;
	}

	@Override
	public void deleteList(ShoppingList list) {
		listService.delete(list);
	}

	@Override
	public ShoppingList saveList(ShoppingList list) {
		ShoppingList saved = listService.save(list);
		ShoppingList detached = wrapDetachService.detach(saved);
		return detached;
	}
}
