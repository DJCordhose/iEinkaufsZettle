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
import eu.zeigermann.gwt.demo.client.item.DefaultItemView;
import eu.zeigermann.gwt.demo.client.item.ItemPresenter;
import eu.zeigermann.gwt.demo.client.item.ItemView;
import eu.zeigermann.gwt.demo.client.list.DefaultShoppingListView;
import eu.zeigermann.gwt.demo.client.list.ShoppingListPresenter;
import eu.zeigermann.gwt.demo.client.list.ShoppingListView;

public class AppController implements ValueChangeHandler<String> {
	private final HandlerManager eventBus;
	private HasWidgets container;

	public AppController(HandlerManager eventBus) {
		this.eventBus = eventBus;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(EditItemsEvent.TYPE, new EditItemsEventHandler() {

			@Override
			public void onEditContact(EditItemsEvent event) {
				doEditItems(event.getList().getId(), true);
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
						view, eventBus);
				presenter.go(container);
			}

			@Override
			public void onFailure(Throwable reason) {
				GWT.log("Loading of module" + ShoppingListPresenter.class
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
				ItemPresenter presenter = new ItemPresenter(listId, view,
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
			if (token.equals("editLists")) {
				doEditLists(false);
			} else if (token.startsWith("editList:")) {
				String[] split = token.split(":");
				if (split.length >= 2) {
					int listId = Integer.valueOf(split[1]);
					doEditItems(listId, false);
				}
			}
		}
	}
}
