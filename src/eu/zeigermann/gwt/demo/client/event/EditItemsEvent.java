package eu.zeigermann.gwt.demo.client.event;

import com.google.gwt.event.shared.GwtEvent;

import eu.zeigermann.gwt.demo.shared.entity.jpa.ShoppingList;

public class EditItemsEvent extends GwtEvent<EditItemsEventHandler> {
	public static Type<EditItemsEventHandler> TYPE = new Type<EditItemsEventHandler>();
	private final ShoppingList list;

	public EditItemsEvent(ShoppingList list) {
		this.list = list;
	}

	public ShoppingList getList() {
		return list;
	}

	@Override
	public Type<EditItemsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditItemsEventHandler handler) {
		handler.onEditItems(this);
	}
}
