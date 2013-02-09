package eu.zeigermann.gwt.demo.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;

import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundary;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryAsync;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryDto;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryDtoAsync;
import eu.zeigermann.gwt.demo.shared.dto.ShoppingListDto;
import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public class MainPresenter implements MainView.EventHandler {

	private ShoppingBoundaryAsync service = GWT.create(ShoppingBoundary.class);
	private ShoppingBoundaryDtoAsync dtoService = GWT
			.create(ShoppingBoundaryDto.class);

	ListDataProvider<ShoppingList> dataProvider = new ListDataProvider<ShoppingList>();
	ShoppingList currentList;
	MainView view;
	
	public void deleteList(final ShoppingList list) {
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

	public void saveList(String name) {
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

	public void createList(String name) {
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

	public void setView(MainView view) {
		this.view = view;
		view.setEventHandler(this);
	}

	public ListDataProvider<ShoppingList> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(ListDataProvider<ShoppingList> dataProvider) {
		this.dataProvider = dataProvider;
	}

	public void editList(ShoppingList list) {
		this.currentList = list;
		view.edit(list);
	}

}
