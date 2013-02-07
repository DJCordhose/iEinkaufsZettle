package eu.zeigermann.gwt.demo.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dozer.Mapper;
import org.springframework.transaction.annotation.Transactional;

import eu.zeigermann.gwt.demo.shared.dto.ShoppingListDto;
import eu.zeigermann.gwt.demo.shared.entity.Item;
import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

@Named
@Transactional
public class ShoppingListService {

	@Inject
	private Mapper mapper;

	@PersistenceContext
	EntityManager em;

	public ShoppingList create() {
		ShoppingList list = new ShoppingList();
		em.persist(list);
		return list;
	}

	public ShoppingList save(final ShoppingList list) {
		if (list.getId() == 0) {
			em.persist(list);
			return list;
		} else {
			return em.merge(list);
		}
	}

	public ShoppingList find(int id) {
		return em.find(ShoppingList.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<ShoppingList> getAll() {
		Query query = em.createNamedQuery(ShoppingList.QUERY_ALL);
		return query.getResultList();
	}

	public ShoppingList detach(ShoppingList list) {
		if (list.getItems() != null) {
			list.setItems(new ArrayList<Item>(list.getItems()));
		}
		return list;
	}

	public ShoppingListDto wrap(ShoppingList list) {
		ShoppingListDto dto = mapper.map(list, ShoppingListDto.class);
		return dto;
	}

}
