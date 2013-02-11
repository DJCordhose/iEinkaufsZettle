package eu.zeigermann.gwt.demo.server.boundary;

import java.util.List;
import java.util.Map;

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
	public ShoppingListDto getList(int listId) {
		ShoppingList list = service.findList(listId);
		ShoppingListDto dto = wrapDetachService.wrap(list);
		return dto;
	}
	
	@Override
	public ShoppingListDto createList(String name) {
		ShoppingList list = service.createList();
		list.setName(name);
		list = service.save(list);
		ShoppingListDto dto = wrapDetachService.wrap(list);
		return dto;
	}

	@Override
	public List<ItemDto> getItems(ShoppingListDto listDto) {
		ShoppingList list = service.findList(listDto.id);
		List<Item> items = list.getItems();
		List<ItemDto> dto = wrapDetachService.wrap(items);
		return dto;
	}

	@Override
	public List<ItemDto> getItems(int listId, int start, int length,
			Map<String, Boolean> sortInfo) {
		ShoppingList list = service.findList(listId);
		List<Item> items = service.getItems(list, start, length, sortInfo);
		List<ItemDto> dto = wrapDetachService.wrap(items);
		return dto;
	}

	@Override
	public int getItemCount(int listId) {
		ShoppingList list = service.findList(listId);
		return service.countItems(list);
	}

	@Override
	public void addItem(ItemDto dto) {
		service.createItem(dto);
	}

	@Override
	public void saveItem(ItemDto dto) {
		Item item = wrapDetachService.unwrap(dto);
		service.save(item);
	}

	@Override
	public void delete(ItemDto dto) {
		Item item = wrapDetachService.unwrap(dto);
		service.removeItem(item);
	}

	@Override
	public void insertItemAfter(ItemDto itemToInsert, ItemDto itemPosition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertItemBefore(ItemDto itemToInsert, ItemDto itemPosition) {
		// TODO Auto-generated method stub
		
	}
}
