package eu.zeigermann.gwt.demo.server.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import eu.zeigermann.gwt.demo.shared.entity.Item;
import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

@Named
// could be optimized to place it only before really transactional methods
// left like this in order not to forget it anywhere
@Transactional
public class ShoppingListService {

	@PersistenceContext
	EntityManager em;

	public ShoppingList create() {
		ShoppingList list = new ShoppingList();
		em.persist(list);
		return list;
	}

	public ShoppingList save(ShoppingList list) {
		if (list.getId() == 0) {
			em.persist(list);
			return list;
		} else {
			return em.merge(list);
		}
	}

	public void delete(ShoppingList list) {
		em.remove(em.merge(list));
	}

	public ShoppingList find(int id) {
		return em.find(ShoppingList.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<ShoppingList> getAll() {
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
				+ " o WHERE o.list = :list";

		if (sortInfo.size() > 0) {
			queryString += " ORDER BY ";
			Set<Entry<String, Boolean>> entries = sortInfo.entrySet();
			int i = 0;
			for (Entry<String, Boolean> entry : entries) {
				queryString += entry.getKey();
				if (!entry.getValue()) {
					queryString += " DESC";
				}
				i++;
				if (i < entries.size()) {
					queryString += ", ";
				}
			}

		}

		Query query = em.createQuery(queryString, Item.class);
		query.setParameter("list", list);
		return query.getResultList();
	}

}
