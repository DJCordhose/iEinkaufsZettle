package eu.zeigermann.gwt.demo.shared.boundary;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.zeigermann.gwt.demo.shared.dto.ItemDto;
import eu.zeigermann.gwt.demo.shared.dto.ShoppingListDto;

public interface ShoppingBoundaryDtoAsync {

	void createList(String name, AsyncCallback<ShoppingListDto> callback);

	void getItems(ShoppingListDto list, AsyncCallback<List<ItemDto>> callback);

	void getItems(int listId, int start, int length, Map<String, Boolean> sortInfo,
			AsyncCallback<List<ItemDto>> callback);

	void getItemCount(int listId, AsyncCallback<Integer> callback);

	void getList(int listId, AsyncCallback<ShoppingListDto> callback);

	void addItem(ItemDto dto, AsyncCallback<Void> callback);

	void saveItem(ItemDto item, AsyncCallback<Void> asyncCallback);

	void delete(ItemDto item, AsyncCallback<Void> asyncCallback);

	void insertItemAfter(ItemDto itemToInsert, ItemDto itemPosition,
			AsyncCallback<Void> callback);

	void insertItemBefore(ItemDto itemToInsert, ItemDto itemPosition,
			AsyncCallback<Void> callback);

}
