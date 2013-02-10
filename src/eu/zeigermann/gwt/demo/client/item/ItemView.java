package eu.zeigermann.gwt.demo.client.item;

import com.google.gwt.user.client.ui.Widget;

import eu.zeigermann.gwt.demo.shared.dto.ItemDto;

public interface ItemView {
	public interface Handler {
		void load();
		void delete(ItemDto item);
		void save(String text);
		void create(String text);
		void edit(ItemDto item);
	}
	void edit(ItemDto item);
	void setPresenter(Handler handler);
	Widget asWidget();
}
