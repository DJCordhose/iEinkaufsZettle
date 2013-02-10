package eu.zeigermann.gwt.demo.client.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.view.client.AbstractDataProvider;
import com.google.gwt.view.client.AsyncDataProvider;

import eu.zeigermann.gwt.demo.client.Presenter;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryDto;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryDtoAsync;
import eu.zeigermann.gwt.demo.shared.dto.ItemDto;
import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public class ItemPresenter implements Presenter<ItemView>, ItemView.Handler {

	private ShoppingBoundaryDtoAsync dtoService = GWT
			.create(ShoppingBoundaryDto.class);

	AsyncDataProvider<ItemDto> dataProvider;
	ShoppingList currentList;
	ItemDto currentItem;
	ItemView view;
	HandlerManager eventBus;

	@Override
	public void setView(ItemView view) {
		this.view = view;
		view.setPresenter(this);
	}

	public AbstractDataProvider<ItemDto> getDataProvider() {
		return dataProvider;
	}

	public void editList(ItemDto item) {
		this.currentItem = item;
		view.edit(item);
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}

	@Override
	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
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

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ItemDto item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(ItemDto item) {
		// TODO Auto-generated method stub
		
	}

}
