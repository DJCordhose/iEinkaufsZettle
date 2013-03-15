package eu.zeigermann.gwt.demo.client.shop;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import eu.zeigermann.gwt.demo.shared.entity.jpa.Shop;

public interface ShopView {
	public interface ViewHandler {
		void load();
		void delete(Shop list);
		void save(String text);
		void create(String text);
		void edit(Shop list);
	}
	void edit(Shop list);
	void setViewHandler(ViewHandler handler);
	Widget asWidget();
	void setDataProvider(ListDataProvider<Shop> dataProvider);
}
