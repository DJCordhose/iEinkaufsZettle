package eu.zeigermann.gwt.demo.shared.boundary;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;


@RemoteServiceRelativePath("list")
public interface ShoppingBoundary extends RemoteService {
	public ShoppingList createList(String name);

}