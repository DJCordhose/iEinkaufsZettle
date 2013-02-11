package eu.zeigermann.gwt.demo.client.list;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public interface ShoppingListView {
	public interface ViewHandler {
		void load();
		void delete(ShoppingList list);
		void save(String text);
		void create(String text);
		void edit(ShoppingList list);
		void editItems();
		void editItems(ShoppingList list);
	}
	void edit(ShoppingList list);
	void setViewHandler(ViewHandler handler);
	Widget asWidget();
	void setDataProvider(ListDataProvider<ShoppingList> dataProvider);
}
