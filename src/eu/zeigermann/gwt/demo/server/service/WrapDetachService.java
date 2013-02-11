package eu.zeigermann.gwt.demo.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dozer.Mapper;
//import org.hibernate.LockMode;
//import org.hibernate.Session;

import eu.zeigermann.gwt.demo.shared.dto.ItemDto;
import eu.zeigermann.gwt.demo.shared.dto.ShoppingListDto;
import eu.zeigermann.gwt.demo.shared.entity.AbstractShoppingEntity;
import eu.zeigermann.gwt.demo.shared.entity.Item;
import eu.zeigermann.gwt.demo.shared.entity.Shop;
import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

@Named
public class WrapDetachService {

	@Inject
	private Mapper mapper;
	
	@Inject
	ShoppingListService service;
	
	@PersistenceContext
	EntityManager em;

	public ShoppingList detach(ShoppingList list) {
		// XXX we should call merge on normal em for a save operation,
		// but as we are very sure here that between detach from persistence context and passing into this method the entity has not been changed
		// we use the less expensive version
//		Session hibernateSession = (Session) em.getDelegate();
//		hibernateSession.lock(list, LockMode.NONE);
		list = em.merge(list);
		if (list.getItems() != null) {
			list.setItems(new ArrayList<Item>(list.getItems()));
		}
		return list;
	}

	// default, Entity geht einfach durch
	public <E extends AbstractShoppingEntity> E detach(E entity) {
		return entity;
	}

	public ShoppingListDto wrap(ShoppingList list) {
		ShoppingListDto dto = mapper.map(list, ShoppingListDto.class);
		return dto;
	}

	public List<ItemDto> wrap(List<Item> items) {
		List<ItemDto> wrapped = new ArrayList<ItemDto>();
		for (Item item : items) {
			ItemDto dto = wrap(item);
			wrapped.add(dto);
		}
		return wrapped;
	}

	public ItemDto wrap(Item item) {
		ItemDto dto = mapper.map(item, ItemDto.class);
		dto.shopName = item.getShop().getName();
		dto.shopId = item.getShop().getId();
		dto.listId = item.getList().getId();
		return dto;
	}

	public Item unwrap(ItemDto dto) {
		Item item = em.find(Item.class, dto.id);
		mapper.map(dto, item);
		return item;
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractShoppingEntity> List<T> detach(List<T> list) {
		List<T> detached = new ArrayList<T>();
		for (T item : list) {
			T dto;
			if (item instanceof ShoppingList) {
				dto = (T) detach((ShoppingList) item);
			} else if (item instanceof Shop) {
				dto = (T) detach((Shop) item);
			} else if (item instanceof Item) {
				dto = (T) detach((Item) item);
			} else {
				dto = (T) detach(item);
			}
			detached.add(dto);
		}
		return detached;
	}

}
