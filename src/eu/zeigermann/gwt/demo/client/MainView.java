package eu.zeigermann.gwt.demo.client;

import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public interface MainView {
	public interface Presenter {
		void load();
		void deleteList(ShoppingList list);
		void saveList(String text);
		void createList(String text);
		void editList(ShoppingList list);
		void editItems();
		
	}
	void edit(ShoppingList list);
	void setPresenter(Presenter handler);

}
