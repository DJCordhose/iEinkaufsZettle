package eu.zeigermann.gwt.demo.server.boundary;

import java.util.List;

import javax.inject.Inject;

import eu.zeigermann.gwt.demo.server.service.ShoppingListService;
import eu.zeigermann.gwt.demo.server.service.WrapDetachService;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryDto;
import eu.zeigermann.gwt.demo.shared.dto.ItemDto;
import eu.zeigermann.gwt.demo.shared.dto.ShoppingListDto;
import eu.zeigermann.gwt.demo.shared.entity.Item;
import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

@SuppressWarnings("serial")
public class ShoppingBoundaryDtoImpl extends AbstractSpringBoundary
		implements ShoppingBoundaryDto {

	@Inject
	ShoppingListService service;
	
	@Inject
	WrapDetachService wrapDetachService;

	@Override
	public ShoppingListDto createList(String name) {
		ShoppingList list = service.create();
		list.setName(name);
		list = service.save(list);
		ShoppingListDto dto = wrapDetachService.wrap(list);
		return dto;
	}

	@Override
	public List<ItemDto> getItems(ShoppingListDto listDto) {
		ShoppingList list = service.find(listDto.id);
		List<Item> items = list.getItems();
		List<ItemDto> dto = wrapDetachService.wrap(items);
		return dto;
	}
}