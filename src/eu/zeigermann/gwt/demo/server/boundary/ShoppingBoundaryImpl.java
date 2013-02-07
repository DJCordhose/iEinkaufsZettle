package eu.zeigermann.gwt.demo.server.boundary;

import javax.inject.Inject;

import eu.zeigermann.gwt.demo.server.service.ShoppingListService;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundary;
import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

@SuppressWarnings("serial")
public class ShoppingBoundaryImpl extends AbstractSpringBoundary
		implements ShoppingBoundary {

	@Inject
	ShoppingListService service;

	@Override
	public ShoppingList createList(String name) {
		ShoppingList list = service.create();
		list.setName(name);
		list = service.save(list);
		ShoppingList detached = service.detach(list);
		return detached;
		
	}
}
