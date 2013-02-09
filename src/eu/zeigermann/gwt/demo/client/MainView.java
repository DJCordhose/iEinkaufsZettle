package eu.zeigermann.gwt.demo.client;

import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public interface MainView {
	public interface EventHandler {
		void load();
		void deleteList(ShoppingList list);
		void saveList(String text);
		void createList(String text);
		void editList(ShoppingList list);
		
	}
	void edit(ShoppingList list);
	void setEventHandler(EventHandler handler);

}
