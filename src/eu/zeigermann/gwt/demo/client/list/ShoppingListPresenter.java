package eu.zeigermann.gwt.demo.client.list;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.view.client.ListDataProvider;

import eu.zeigermann.gwt.demo.client.Presenter;
import eu.zeigermann.gwt.demo.client.event.EditItemsEvent;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundary;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryAsync;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryDto;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryDtoAsync;
import eu.zeigermann.gwt.demo.shared.dto.ShoppingListDto;
import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public class ShoppingListPresenter implements Presenter<ShoppingListView>, ShoppingListView.Handler {

	private ShoppingBoundaryAsync service = GWT.create(ShoppingBoundary.class);
	private ShoppingBoundaryDtoAsync dtoService = GWT
			.create(ShoppingBoundaryDto.class);

	ListDataProvider<ShoppingList> dataProvider = new ListDataProvider<ShoppingList>();
	ShoppingList currentList;
	ShoppingListView view;
	HandlerManager eventBus;

	public void delete(final ShoppingList list) {
		service.deleteList(list, new AsyncCallback<Void>() {

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
		if (currentList == null) {
			throw new IllegalStateException(
					"Can not save without list being edited");
		}
		currentList.setName(name);
		service.saveList(currentList, new AsyncCallback<ShoppingList>() {

			@Override
			public void onSuccess(ShoppingList result) {
				int index = dataProvider.getList().indexOf(currentList);
				if (index != -1) {
					dataProvider.getList().set(index, result);
				} else {
					dataProvider.getList().add(result);
				}
				currentList = null;
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}
		});
	}

	public void create(String name) {
		service.createList(name, new AsyncCallback<ShoppingList>() {

			@Override
			public void onSuccess(ShoppingList list) {
				dataProvider.getList().add(list);
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}
		});
	}

	public void createListAsDto(String name) {
		dtoService.createList(name, new AsyncCallback<ShoppingListDto>() {

			@Override
			public void onSuccess(ShoppingListDto list) {
				// dataProvider.getList().add(list);
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}
		});
	}

	public void load() {
		service.getAllLists(new AsyncCallback<List<ShoppingList>>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}

			@Override
			public void onSuccess(List<ShoppingList> result) {
				dataProvider.getList().clear();
				dataProvider.getList().addAll(result);
			}
		});
	}

	@Override
	public void setView(ShoppingListView view) {
		this.view = view;
		view.setPresenter(this);
	}

	public ListDataProvider<ShoppingList> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(ListDataProvider<ShoppingList> dataProvider) {
		this.dataProvider = dataProvider;
	}

	public void edit(ShoppingList list) {
		this.currentList = list;
		view.edit(list);
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}

	@Override
	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void editItems() {
		if (currentList == null) {
			throw new IllegalStateException(
					"Can not edit items without list being edited");
		}
		if (eventBus == null) {
			throw new IllegalStateException(
					"No eventsbus set");
		}
		eventBus.fireEvent(new EditItemsEvent(currentList));
	}

	@Override
	public void go(HasWidgets container) {
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
