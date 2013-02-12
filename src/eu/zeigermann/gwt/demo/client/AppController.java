package eu.zeigermann.gwt.demo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import eu.zeigermann.gwt.demo.client.event.EditItemsEvent;
import eu.zeigermann.gwt.demo.client.event.EditItemsEventHandler;
import eu.zeigermann.gwt.demo.client.event.EditShopEvent;
import eu.zeigermann.gwt.demo.client.event.EditShopEventHandler;
import eu.zeigermann.gwt.demo.client.item.DefaultItemView;
import eu.zeigermann.gwt.demo.client.item.ItemPresenter;
import eu.zeigermann.gwt.demo.client.item.ItemView;
import eu.zeigermann.gwt.demo.client.list.DefaultShoppingListView;
import eu.zeigermann.gwt.demo.client.list.ShoppingListPresenter;
import eu.zeigermann.gwt.demo.client.list.ShoppingListView;
import eu.zeigermann.gwt.demo.client.shop.DefaultShopView;
import eu.zeigermann.gwt.demo.client.shop.ShopPresenter;
import eu.zeigermann.gwt.demo.client.shop.ShopView;
import eu.zeigermann.gwt.demo.client.statistics.DefaultStatisticsView;
import eu.zeigermann.gwt.demo.client.statistics.StatisticsPresenter;
import eu.zeigermann.gwt.demo.client.statistics.StatisticsView;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundary;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryAsync;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryDto;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryDtoAsync;

public class AppController implements ValueChangeHandler<String> {
	private final HandlerManager eventBus;
	private HasWidgets container;
	private ShoppingBoundaryAsync service = GWT.create(ShoppingBoundary.class);
	private ShoppingBoundaryDtoAsync dtoService = GWT
			.create(ShoppingBoundaryDto.class);

	public AppController(HandlerManager eventBus) {
		this.eventBus = eventBus;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(EditItemsEvent.TYPE, new EditItemsEventHandler() {

			@Override
			public void onEditItems(EditItemsEvent event) {
				doEditItems(event.getList().getId(), true);
			}
		});
		eventBus.addHandler(EditShopEvent.TYPE, new EditShopEventHandler() {

			@Override
			public void onEditShop(EditShopEvent event) {
				doEditShops(event.getShopName(), true);
			}
		});
	}

	private void doStatistics(boolean createHistory) {
		if (createHistory) {
			History.newItem("statistics", false);
		}
		GWT.runAsync(StatisticsPresenter.class, new RunAsyncCallback() {

			@Override
			public void onSuccess() {
				StatisticsView view = new DefaultStatisticsView();
				StatisticsPresenter presenter = new StatisticsPresenter(
						service, view, eventBus);
				presenter.go(container);
			}

			@Override
			public void onFailure(Throwable reason) {
				GWT.log("Loading of module" + ShoppingListPresenter.class
						+ " failed for reason: " + reason);
			}
		});
	}

	private void doEditLists(boolean createHistory) {
		if (createHistory) {
			History.newItem("editLists", false);
		}
		GWT.runAsync(ShoppingListPresenter.class, new RunAsyncCallback() {

			@Override
			public void onSuccess() {
				ShoppingListView view = new DefaultShoppingListView();
				ShoppingListPresenter presenter = new ShoppingListPresenter(
						service, view, eventBus);
				presenter.go(container);
			}

			@Override
			public void onFailure(Throwable reason) {
				GWT.log("Loading of module" + ShoppingListPresenter.class
						+ " failed for reason: " + reason);
			}
		});
	}

	private void doEditShops(final String shopName, boolean createHistory) {
		if (createHistory) {
			if (shopName != null) {
				History.newItem("editShops:" + shopName, false);
			} else {
				History.newItem("editShops", false);
			}
		}
		GWT.runAsync(ShopPresenter.class, new RunAsyncCallback() {

			@Override
			public void onSuccess() {
				ShopView view = new DefaultShopView();
				ShopPresenter presenter = new ShopPresenter(service, shopName,
						view, eventBus);
				presenter.go(container);
			}

			@Override
			public void onFailure(Throwable reason) {
				GWT.log("Loading of module" + ShopPresenter.class
						+ " failed for reason: " + reason);
			}
		});
	}

	private void doEditItems(final int listId, boolean createHistory) {
		if (createHistory) {
			History.newItem("editList:" + listId, false);
		}
		GWT.runAsync(ItemPresenter.class, new RunAsyncCallback() {

			@Override
			public void onSuccess() {
				ItemView view = new DefaultItemView();
				ItemPresenter presenter = new ItemPresenter(service, dtoService, listId, view,
						eventBus);
				presenter.go(container);
			}

			@Override
			public void onFailure(Throwable reason) {
				GWT.log("Loading of module" + ItemPresenter.class
						+ " failed for reason: " + reason);
			}
		});
	}

	public void go(final HasWidgets container) {
		this.container = container;

		if ("".equals(History.getToken())) {
			History.newItem("editLists");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {
			if (token.equalsIgnoreCase("editLists")) {
				doEditLists(false);
			} else if (token.startsWith("editShops")) {
				String[] split = token.split(":");
				final String shopName;
				if (split.length >= 2) {
					shopName = split[1];
				} else {
					shopName = null;
				}
				doEditShops(shopName, false);
			} else if (token.startsWith("editList:")) {
				String[] split = token.split(":");
				if (split.length >= 2) {
					int listId = Integer.valueOf(split[1]);
					doEditItems(listId, false);
				}
			} else if (token.equalsIgnoreCase("statistics")) {
				doStatistics(false);
			}
		}
	}

}
