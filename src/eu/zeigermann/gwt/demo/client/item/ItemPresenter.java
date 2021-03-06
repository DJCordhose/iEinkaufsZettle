package eu.zeigermann.gwt.demo.client.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.view.client.AbstractDataProvider;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

import eu.zeigermann.gwt.demo.client.Presenter;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryAsync;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryDtoAsync;
import eu.zeigermann.gwt.demo.shared.dto.ItemDto;
import eu.zeigermann.gwt.demo.shared.dto.ShoppingListDto;
import eu.zeigermann.gwt.demo.shared.entity.jpa.Shop;

public class ItemPresenter implements Presenter<ItemView>, ItemView.ViewHandler {

	AsyncDataProvider<ItemDto> dataProvider;
	ItemDto currentItem;
	final int currentListId;
	final ItemView view;
	final HandlerManager eventBus;
	final ShoppingBoundaryDtoAsync dtoService;
	final ShoppingBoundaryAsync service;

	public ItemPresenter(ShoppingBoundaryAsync service, ShoppingBoundaryDtoAsync dtoService, int listId, ItemView view, HandlerManager eventBus) {
		this.service = service;
		this.dtoService = dtoService;
		this.currentListId = listId;
		this.view = view;
		this.eventBus = eventBus;
		this.dataProvider = new AsyncDataProvider<ItemDto>() {
			@Override
			protected void onRangeChanged(HasData<ItemDto> display) {
				Range range = display.getVisibleRange();
				ColumnSortList sortList = ItemPresenter.this.view
						.getTableColumnSortList();
				loadData(range, sortList);
			}
		};
		view.setViewHandler(this);
		view.setDataProvider(dataProvider);
	}

	public AbstractDataProvider<ItemDto> getDataProvider() {
		return dataProvider;
	}

	public void editItem(ItemDto item) {
		this.currentItem = item;
		view.edit(item);
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		if (view == null) {
			throw new IllegalStateException("No view set");
		}
		if (eventBus == null) {
			throw new IllegalStateException("No eventsbus set");
		}
		container.add(view.asWidget());
		init();
	}

	@Override
	public void init() {
		updateRowCount();
		updateListName();
		initShops();
	}

	private void initShops() {
		service.getAllShops(new AsyncCallback<List<Shop>>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}

			@Override
			public void onSuccess(List<Shop> shops) {
				view.setShops(shops);
			}
		});
	}

	public void updateListName() {
		dtoService.getList(currentListId, new AsyncCallback<ShoppingListDto>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}

			@Override
			public void onSuccess(ShoppingListDto list) {
				view.setListName(list.getName());
			}
		});
	}

	public void updateRowCount() {
		dtoService.getItemCount(currentListId, new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}

			@Override
			public void onSuccess(Integer rows) {
				view.setTableRowCount(rows);
			}
		});
	}

	@Override
	public void loadData(Range range, ColumnSortList sortList) {
		// XXX this data structure looses the order of sorting, but it is good
		// enough to get the point across
		Map<String, Boolean> sortInfo = new HashMap<String, Boolean>();
		for (int i = 0; i < sortList.size(); i++) {
			ColumnSortInfo columnSortInfo = sortList.get(0);
			boolean ascending = columnSortInfo.isAscending();
			String dataStoreName = columnSortInfo.getColumn()
					.getDataStoreName();
			if (dataStoreName == null) {
				throw new IllegalArgumentException(
						"If you want to sort a column on the server, give it a data store name!");
			}
			sortInfo.put(dataStoreName, ascending);

		}
		final int start = range.getStart();
		int length = range.getLength();

		dtoService.getItems(currentListId, start, length, sortInfo,
				new AsyncCallback<List<ItemDto>>() {

					@Override
					public void onSuccess(List<ItemDto> data) {
						view.setVisibleTableData(start, data);
					}

					@Override
					public void onFailure(Throwable caught) {
						GWT.log("Error: " + caught);
					}
				});

	}

	@Override
	public void delete(ItemDto item) {
		dtoService.delete(item, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}

			@Override
			public void onSuccess(Void result) {
				refresh();
			}
		});
	}

	@Override
	public void save(String name) {
		if (currentItem == null) {
			throw new IllegalStateException(
					"Can not save without item being edited");
		}
		currentItem.setName(name);
		saveItem(currentItem);
	}

	private void saveItem(ItemDto item) {
		dtoService.saveItem(item, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void v) {
				refresh();
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}
		});
	}

	@Override
	public void create(String name) {
		ItemDto itemDto = new ItemDto(name);
		itemDto.listId = currentListId;
		itemDto.shopId = view.getShopId();
		dtoService.addItem(itemDto, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void v) {
				refresh();
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}
		});
	}

	public void move(ItemDto toMove, ItemDto toPosition) {
		dtoService.insertItemAfter(toMove, toPosition, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}

			@Override
			public void onSuccess(Void result) {
				refresh();
			}
		});
	}
	
	@Override
	public void edit(ItemDto item) {
		this.currentItem = item;
		view.edit(item);
	}

	private void refresh() {
		view.refresh();
		updateRowCount();
	}

	@Override
	public void check(ItemDto item, Boolean value) {
		item.setChecked(value);
		saveItem(item);
	}

}
