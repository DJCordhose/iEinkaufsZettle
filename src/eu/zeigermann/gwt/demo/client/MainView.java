package eu.zeigermann.gwt.demo.client;

import com.google.gwt.user.client.ui.Widget;

import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public interface MainView {
	public interface Handler extends Presenter {
		void load();
		void deleteList(ShoppingList list);
		void saveList(String text);
		void createList(String text);
		void editList(ShoppingList list);
		void editItems();
	}
	void edit(ShoppingList list);
	void setPresenter(Handler handler);
	Widget asWidget();
}
