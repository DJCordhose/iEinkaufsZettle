package eu.zeigermann.gwt.demo.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import eu.zeigermann.gwt.demo.shared.dto.ItemDto;
import eu.zeigermann.gwt.demo.shared.entity.jpa.AbstractShoppingEntity;
import eu.zeigermann.gwt.demo.shared.entity.jpa.Item;
import eu.zeigermann.gwt.demo.shared.entity.jpa.Shop;
import eu.zeigermann.gwt.demo.shared.entity.jpa.ShoppingList;

@Named
// could be optimized to place it only before really transactional methods
// left like this in order not to forget it anywhere
@Transactional
public class ShoppingListService {

	@PersistenceContext
	EntityManager em;

	public ShoppingList createList() {
		ShoppingList list = new ShoppingList();
		em.persist(list);
		return list;
	}

	public ShoppingList findList(int id) {
		ShoppingList shoppingList = em.find(ShoppingList.class, id);
		List<Item> items = shoppingList.getItems();
		return shoppingList;
	}

	@SuppressWarnings("unchecked")
	public List<ShoppingList> getAllLists() {
		Query query = em.createNamedQuery(ShoppingList.QUERY_ALL);
		return query.getResultList();
	}

	public int countItems(ShoppingList list) {
		Query query = em.createNamedQuery(Item.QUERY_COUNT_ITEMS);
		query.setParameter("list", list);
		Long rows = (Long) query.getSingleResult();
		return rows.intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Item> getItems(ShoppingList list, int start, int length,
			Map<String, Boolean> sortInfo) {
		// XXX this really is brute force, maybe consider using a criteria query
		String queryString = "SELECT o from " + Item.TABLE
				+ " o WHERE o.list = :list" + " ORDER BY ";

		Set<Entry<String, Boolean>> entries = sortInfo.entrySet();
		for (Entry<String, Boolean> entry : entries) {
			queryString += entry.getKey();
			if (!entry.getValue()) {
				queryString += " DESC";
			}
			queryString += ", ";
		}
		// always sort by position
		queryString += "position ASC";

		Query query = em.createQuery(queryString, Item.class);
		query.setMaxResults(length);
		query.setFirstResult(start);
		query.setParameter("list", list);
		return query.getResultList();
	}

	public Item createItem(ItemDto dto) {
		Item item = new Item();
		item.setName(dto.name);
		item.setChecked(dto.checked);
		if (dto.shopId != -1) {
			Shop shop = findShop(dto.shopId);
			item.setShop(shop);
		}
		ShoppingList list = findList(dto.listId);
		list.addItem(item);
		return item;
	}

	public void removeItem(Item item) {
		item = em.merge(item);
		ShoppingList list = item.getList();
		list.getItems().remove(item);
		em.remove(item);
	}

	public <E extends AbstractShoppingEntity> E save(E entity) {
		if (entity.getId() == 0) {
			em.persist(entity);
			return entity;
		} else {
			return em.merge(entity);
		}
	}

	public <E extends AbstractShoppingEntity> void delete(E entity) {
		em.remove(em.merge(entity));
	}

	public Shop findShop(int shopId) {
		return em.find(Shop.class, shopId);
	}

	public Item findItem(int itemId) {
		return em.find(Item.class, itemId);
	}

	@SuppressWarnings("unchecked")
	public List<Shop> getAllShops() {
		Query query = em.createNamedQuery(Shop.QUERY_ALL);
		return query.getResultList();
	}

	public Shop createShop() {
		Shop shop = new Shop();
		em.persist(shop);
		return shop;
	}

	public void moveItem(Item itemToMove, Item position, boolean after) {
		position = em.merge(position);
		itemToMove = em.merge(itemToMove);
		ShoppingList list = position.getList();
		List<Item> items = list.getItems();
		items.remove(itemToMove);
		int index = items.indexOf(position);
		items.add(index + (after ? 1 : 0), itemToMove);
		list.refreshPositions();
	}
	
	public Map<String, Integer> statistics() {
		// init data with all shops having count 0
		Map<String, Integer> data = new HashMap<String, Integer>();
		List<Shop> allShops = getAllShops();
		for (Shop shop : allShops) {
			data.put(shop.getName(), 0);
		}
		// XXX could be done more efficiently using a dedicated query, but we do not care :)
		List<ShoppingList> allLists = getAllLists();
		for (ShoppingList list : allLists) {
			List<Item> items = list.getItems();
			for (Item item : items) {
				Shop shop = item.getShop();
				int count = data.get(shop.getName());
				count++;
				data.put(shop.getName(), count);
			}
		}
		return data;
	}

}
