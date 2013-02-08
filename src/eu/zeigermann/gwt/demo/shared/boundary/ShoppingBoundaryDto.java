package eu.zeigermann.gwt.demo.shared.boundary;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import eu.zeigermann.gwt.demo.shared.dto.ItemDto;
import eu.zeigermann.gwt.demo.shared.dto.ShoppingListDto;

@RemoteServiceRelativePath("dto")
public interface ShoppingBoundaryDto extends RemoteService {
	ShoppingListDto createList(String name);

	List<ItemDto> getItems(ShoppingListDto list);
}