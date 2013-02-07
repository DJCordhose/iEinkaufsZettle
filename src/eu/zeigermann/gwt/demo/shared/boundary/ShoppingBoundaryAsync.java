package eu.zeigermann.gwt.demo.shared.boundary;

import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public interface ShoppingBoundaryAsync {

	void createList(String name, AsyncCallback<ShoppingList> callback);

}
