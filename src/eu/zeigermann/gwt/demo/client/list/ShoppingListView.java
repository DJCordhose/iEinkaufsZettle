package eu.zeigermann.gwt.demo.client.list;

import com.google.gwt.user.client.ui.Widget;

import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public interface ShoppingListView {
	public interface Handler {
		void load();
		void delete(ShoppingList list);
		void save(String text);
		void create(String text);
		void edit(ShoppingList list);
		void editItems();
	}
	void edit(ShoppingList list);
	void setPresenter(Handler handler);
	Widget asWidget();
}
