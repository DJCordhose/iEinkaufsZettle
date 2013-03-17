package eu.zeigermann.gwt.demo.server.boundary;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import eu.zeigermann.gwt.demo.server.service.jpa.ShoppingListService;
import eu.zeigermann.gwt.demo.server.service.jpa.WrapDetachService;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundary;
import eu.zeigermann.gwt.demo.shared.entity.jpa.Shop;
import eu.zeigermann.gwt.demo.shared.entity.jpa.ShoppingList;

@SuppressWarnings("serial")
public class ShoppingBoundaryImpl extends AbstractSpringBoundary
		implements ShoppingBoundary {

	@Inject
	ShoppingListService service;

	@Inject
	WrapDetachService wrapDetachService;
	
	@Override
	public ShoppingList createList(String name) {
		ShoppingList list = service.createList();
		list.setName(name);
		list = service.save(list);
		ShoppingList detached = wrapDetachService.detach(list);
		return detached;
	}
	
	@Override
	public List<ShoppingList> getAllLists() {
		List<ShoppingList> all = service.getAllLists();
		List<ShoppingList> detached = wrapDetachService.detach(all);
		return detached;
	}

	@Override
	public void deleteList(ShoppingList list) {
		service.delete(list);
	}

	@Override
	public ShoppingList saveList(ShoppingList list) {
		ShoppingList saved = service.save(list);
		ShoppingList detached = wrapDetachService.detach(saved);
		return detached;
	}

	@Override
	public void delete(Shop list) {
		service.delete(list);
	}

	@Override
	public Shop createShop(String name) {
		Shop shop = service.createShop();
		shop.setName(name);
		shop = service.save(shop);
		Shop detached = wrapDetachService.detach(shop);
		return detached;
	}

	@Override
	public List<Shop> getAllShops() {
		List<Shop> all = service.getAllShops();
		List<Shop> detached = wrapDetachService.detach(all);
		return detached;
	}

	@Override
	public Shop save(Shop shop) {
		Shop saved = service.save(shop);
		Shop detached = wrapDetachService.detach(saved);
		return detached;
	}
	
	@Override
	public Map<String, Integer> statistics() {
		return service.statistics();
	}
}
