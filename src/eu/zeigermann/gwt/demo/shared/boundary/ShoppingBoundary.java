package eu.zeigermann.gwt.demo.shared.boundary;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import eu.zeigermann.gwt.demo.shared.entity.Shop;
import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

@RemoteServiceRelativePath("list")
public interface ShoppingBoundary extends RemoteService {
	ShoppingList createList(String name);

	List<ShoppingList> getAllLists();

	void deleteList(ShoppingList list);
	
	ShoppingList saveList(ShoppingList list);

	void delete(Shop list);

	Shop createShop(String name);

	List<Shop> getAllShops();

	Shop save(Shop currentEntity);
	

}