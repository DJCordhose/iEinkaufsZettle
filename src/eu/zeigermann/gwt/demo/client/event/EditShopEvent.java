package eu.zeigermann.gwt.demo.client.event;

import com.google.gwt.event.shared.GwtEvent;

import eu.zeigermann.gwt.demo.shared.entity.jpa.ShoppingList;

public class EditShopEvent extends GwtEvent<EditShopEventHandler> {
	public static Type<EditShopEventHandler> TYPE = new Type<EditShopEventHandler>();
	private final String shopName;

	public EditShopEvent(String shopName) {
		this.shopName = shopName;
	}

	public String getShopName() {
		return shopName;
	}

	@Override
	public Type<EditShopEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditShopEventHandler handler) {
		handler.onEditShop(this);
	}
}
