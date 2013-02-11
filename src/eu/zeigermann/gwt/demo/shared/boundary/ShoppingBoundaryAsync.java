package eu.zeigermann.gwt.demo.shared.boundary;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.zeigermann.gwt.demo.shared.entity.Shop;
import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public interface ShoppingBoundaryAsync {

	void createList(String name, AsyncCallback<ShoppingList> callback);

	void getAllLists(AsyncCallback<List<ShoppingList>> callback);

	void deleteList(ShoppingList list, AsyncCallback<Void> asyncCallback);

	void saveList(ShoppingList list, AsyncCallback<ShoppingList> asyncCallback);

	void delete(Shop list, AsyncCallback<Void> asyncCallback);

	void save(Shop currentEntity, AsyncCallback<Shop> asyncCallback);

	void createShop(String name, AsyncCallback<Shop> asyncCallback);

	void getAllShops(AsyncCallback<List<Shop>> asyncCallback);

}
