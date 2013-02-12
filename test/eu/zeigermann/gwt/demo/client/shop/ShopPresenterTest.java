package eu.zeigermann.gwt.demo.client.shop;

import static org.easymock.EasyMock.createStrictMock;
import junit.framework.TestCase;

import com.google.gwt.event.shared.HandlerManager;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryAsync;
import eu.zeigermann.gwt.demo.shared.entity.Shop;

public class ShopPresenterTest extends TestCase {
	private static final String REQUESTED_SHOP = "Aldi";
	ShopPresenter presenter;
	ShoppingBoundaryAsync service;
	HandlerManager eventBus;
	ShopView view;

	protected void setUp() {
		eventBus = new HandlerManager(null);
		service = createStrictMock(ShoppingBoundaryAsync.class);
		view = createStrictMock(ShopView.class);
		presenter = new ShopPresenter(service, REQUESTED_SHOP, view,
				eventBus);
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
