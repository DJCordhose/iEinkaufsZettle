package eu.zeigermann.gwt.demo.client.shop;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.view.client.ListDataProvider;

import eu.zeigermann.gwt.demo.client.Presenter;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryAsync;
import eu.zeigermann.gwt.demo.shared.entity.jpa.Shop;

public class ShopPresenter implements Presenter<ShopView>, ShopView.ViewHandler {

	ListDataProvider<Shop> dataProvider = new ListDataProvider<Shop>();
	Shop currentEntity;
	final String requestedShopName;
	final ShopView view;
	final HandlerManager eventBus;
	final ShoppingBoundaryAsync service;
	
	public ShopPresenter(ShoppingBoundaryAsync service, String requestedShopName, ShopView view, HandlerManager eventBus) {
		this.service = service;
		this.requestedShopName = requestedShopName;
		this.view = view;
		view.setViewHandler(this);
		view.setDataProvider(dataProvider);
		this.eventBus = eventBus;
	}

	public void delete(final Shop list) {
		service.delete(list, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void v) {
				dataProvider.getList().remove(list);
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}
		});
	}

	public void save(String name) {
		if (currentEntity == null) {
			throw new IllegalStateException(
					"Can not save without list being edited");
		}
		currentEntity.setName(name);
		service.save(currentEntity, new AsyncCallback<Shop>() {

			@Override
			public void onSuccess(Shop result) {
				int index = dataProvider.getList().indexOf(currentEntity);
				if (index != -1) {
					dataProvider.getList().set(index, result);
				} else {
					dataProvider.getList().add(result);
				}
				currentEntity = null;
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}
		});
	}

	public void create(String name) {
		service.createShop(name, new AsyncCallback<Shop>() {

			@Override
			public void onSuccess(Shop list) {
				dataProvider.getList().add(list);
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}
		});
	}

	public void load() {
		service.getAllShops(new AsyncCallback<List<Shop>>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}

			@Override
			public void onSuccess(List<Shop> result) {
				dataProvider.getList().clear();
				dataProvider.getList().addAll(result);
				if (requestedShopName != null) {
					for (Shop shop : result) {
						if (shop.getName().equals(requestedShopName)) {
							edit(shop);
						}
					}
				}
			}
		});
	}


	public ListDataProvider<Shop> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(ListDataProvider<Shop> dataProvider) {
		this.dataProvider = dataProvider;
	}

	public void edit(Shop shop) {
		this.currentEntity = shop;
		view.edit(shop);
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		if (view == null) {
			throw new IllegalStateException(
					"No view set");
		}
		if (eventBus == null) {
			throw new IllegalStateException(
					"No eventsbus set");
		}
		container.add(view.asWidget());
		load();
	}

}
