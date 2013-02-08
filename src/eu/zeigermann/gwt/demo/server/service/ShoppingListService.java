package eu.zeigermann.gwt.demo.server.service;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

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
}
