package eu.zeigermann.gwt.demo.client.shop;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.FlowPanel;

import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundary;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryAsync;
import eu.zeigermann.gwt.demo.shared.entity.Shop;

public class ShopPresenterGWTTest extends GWTTestCase {
	private static final String REQUESTED_SHOP = "Aldi";
	ShopPresenter presenter;
	ShoppingBoundaryAsync service;
	HandlerManager eventBus;
	ShopView view;
    
	protected void gwtSetUp() {
		eventBus = new HandlerManager(null);
		service = GWT.create(ShoppingBoundary.class);
		view = new DefaultShopView();
		presenter = new ShopPresenter(service, REQUESTED_SHOP, view,
				eventBus);
		FlowPanel dummyContainer = new FlowPanel();
		presenter.go(dummyContainer);
	}
	
	@Override
	public String getModuleName() {
		return "eu.zeigermann.gwt.demo.Zettle";
	}

	public void testRequestedShop() {
		assertEquals(REQUESTED_SHOP, presenter.requestedShopName);
	}
	
	public void testEdit() {
		Shop shop = new Shop();
		presenter.edit(shop);
		assertEquals(shop, presenter.currentEntity);
	}

}
