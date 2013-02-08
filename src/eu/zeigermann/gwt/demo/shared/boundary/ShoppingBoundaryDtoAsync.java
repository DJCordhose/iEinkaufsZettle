package eu.zeigermann.gwt.demo.shared.boundary;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.zeigermann.gwt.demo.shared.dto.ItemDto;
import eu.zeigermann.gwt.demo.shared.dto.ShoppingListDto;

public interface ShoppingBoundaryDtoAsync {

	void createList(String name, AsyncCallback<ShoppingListDto> callback);

	void getItems(ShoppingListDto list, AsyncCallback<List<ItemDto>> callback);

}
