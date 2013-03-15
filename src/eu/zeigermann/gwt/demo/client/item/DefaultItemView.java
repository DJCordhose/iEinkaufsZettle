/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package eu.zeigermann.gwt.demo.client.item;

import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.Range;

import eu.zeigermann.gwt.demo.shared.dto.ItemDto;
import eu.zeigermann.gwt.demo.shared.entity.jpa.Shop;

public class DefaultItemView extends Composite implements ItemView {

	enum Mode {
		EDIT, CREATE
	};

	interface Binder extends UiBinder<Widget, DefaultItemView> {
	}

	@UiField(provided = true)
	CellTable<ItemDto> cellTable;

	@UiField(provided = true)
	SimplePager pager;

	@UiField
	TextBox nameTextBox;

	@UiField
	Button createButton;

	@UiField
	Button clearButton;

	@UiField
	InlineLabel shoppingListText;

	@UiField
	ListBox shopList;

	private ItemView.ViewHandler presenter;

	private Mode mode;

	private AsyncDataProvider<ItemDto> dataProvider;

	@Override
	public void setDataProvider(AsyncDataProvider<ItemDto> dataProvider) {
		this.dataProvider = dataProvider;
		initWidget(createWidget(dataProvider));
		setMode(Mode.CREATE);
	}

	Widget createWidget(AsyncDataProvider<ItemDto> dataProvider) {
		initTable(dataProvider);

		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);

		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,
				true);
		pager.setDisplay(cellTable);
		Binder uiBinder = GWT.create(Binder.class);
		Widget widget = uiBinder.createAndBindUi(this);

		return widget;
	}

	@UiHandler("clearButton")
	public void clear(ClickEvent event) {
		reset();
	}

	@UiHandler("createButton")
	public void create(ClickEvent event) {
		createOrSave();
	}

	@UiHandler("nameTextBox")
	public void enter(KeyPressEvent event) {
		if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
			createOrSave();
		} else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
			reset();
		}
	}

	private void createOrSave() {
		String text = nameTextBox.getText();
		if (text.length() != 0) {
			if (mode == Mode.EDIT) {
				presenter.save(text);
			} else {
				presenter.create(text);
			}
		}
		reset();
	}

	public void reset() {
		nameTextBox.setText("");
		nameTextBox.setFocus(true);
		setMode(Mode.CREATE);
	}

	@Override
	public void setViewHandler(ViewHandler handler) {
		this.presenter = handler;
	}

	@Override
	public void edit(ItemDto item) {
		nameTextBox.setText(item.getName());
		for (int i = 0; i < shopList.getItemCount(); i++) {
			if (shopList.getValue(i).equals("" + item.shopId)) {
				shopList.setSelectedIndex(i);
			}
		}
		nameTextBox.setFocus(true);
		setMode(Mode.EDIT);
	}

	private void setMode(Mode mode) {
		this.mode = mode;
		if (mode == Mode.EDIT) {
			createButton.setText("Save");
			clearButton.setVisible(true);
		} else {
			createButton.setText("Create");
			clearButton.setVisible(false);
		}
	}

	private void initTable(AsyncDataProvider<ItemDto> dataProvider) {
		final ProvidesKey<ItemDto> keyProvider = new ProvidesKey<ItemDto>() {
			public Object getKey(ItemDto list) {
				return list == null ? null : list.getId();
			}
		};
		cellTable = new CellTable<ItemDto>(keyProvider);
		dataProvider.addDataDisplay(cellTable);
		cellTable.setPageSize(5);

		cellTable.sinkEvents(Event.ONDBLCLICK);
		final MultiSelectionModel<ItemDto> selectionModel = new MultiSelectionModel<ItemDto>(
				keyProvider);
		cellTable.setSelectionModel(selectionModel,
				new CellPreviewEvent.Handler<ItemDto>() {

					@Override
					public void onCellPreview(CellPreviewEvent<ItemDto> event) {
						int eventType = Event.getTypeInt(event.getNativeEvent()
								.getType());
						if (eventType == Event.ONDBLCLICK) {
							ItemDto list = event.getValue();
							presenter.edit(list);
						}
					}
				});

		// styles

		// bootstrap
		cellTable.addStyleName("table");
		cellTable.addStyleName("table-bordered");
		// makes selection invisible on every even row
		// cellTable.addStyleName("table-striped");

		AsyncHandler columnSortHandler = new AsyncHandler(cellTable);

		// columns
		addShopColumn(columnSortHandler);
		addNameColumn();
		addCheckedColumn();
		addEditColumn();
		addDeleteColumn();
	}

	@Override
	public void setVisibleTableData(int start, List<? extends ItemDto> data) {
		cellTable.setRowData(start, data);
	}

	@Override
	public void setTableRowCount(int rows) {
		cellTable.setRowCount(rows, true);
	}

	private void addShopColumn(ColumnSortEvent.Handler columnSortHandler) {
		TextColumn<ItemDto> shopColumn = new TextColumn<ItemDto>() {
			@Override
			public String getValue(ItemDto dto) {
				return dto.shopName;
			}
		};
		shopColumn.setSortable(true);
		shopColumn.setDataStoreName("shop.name");
		cellTable.addColumn(shopColumn, "Shop");
		cellTable.setColumnWidth(shopColumn, 500, Style.Unit.PX);

		cellTable.addColumnSortHandler(columnSortHandler);
		cellTable.getColumnSortList().push(shopColumn);

	}

	private void addNameColumn() {
		TextColumn<ItemDto> nameColumn = new TextColumn<ItemDto>() {
			@Override
			public String getValue(ItemDto dto) {
				return dto.getName();
			}
		};
		nameColumn.setSortable(true);
		nameColumn.setDataStoreName("name");
		cellTable.addColumn(nameColumn, "Name");
		cellTable.setColumnWidth(nameColumn, 500, Style.Unit.PX);
	}

	private void addCheckedColumn() {
		Column<ItemDto, Boolean> checkedColumn = new Column<ItemDto, Boolean>(
				new CheckboxCell()) {
			@Override
			public Boolean getValue(ItemDto object) {
				return object.isChecked();
			}
		};
		checkedColumn.setFieldUpdater(new FieldUpdater<ItemDto, Boolean>() {

			@Override
			public void update(int index, ItemDto object, Boolean value) {
				presenter.check(object, value);
			}
		});

		cellTable.addColumn(checkedColumn,
				SafeHtmlUtils.fromSafeConstant("Checked"));
	}

	private void addEditColumn() {
		Column<ItemDto, ItemDto> deleteColumn = new Column<ItemDto, ItemDto>(
				new ActionCell<ItemDto>(SafeHtmlUtils
						.fromSafeConstant("<i class='icon-edit'></i>"),
						new Delegate<ItemDto>() {
							@Override
							public void execute(final ItemDto item) {
								presenter.edit(item);
							}
						})) {
			@Override
			public ItemDto getValue(ItemDto object) {
				return object;
			}
		};
		cellTable.addColumn(deleteColumn,
				SafeHtmlUtils.fromSafeConstant("<br/>"));
	}

	private void addDeleteColumn() {
		Column<ItemDto, ItemDto> deleteColumn = new Column<ItemDto, ItemDto>(
				new ActionCell<ItemDto>(SafeHtmlUtils
						.fromSafeConstant("<i class='icon-remove'></i>"),
						new Delegate<ItemDto>() {
							@Override
							public void execute(final ItemDto item) {
								presenter.delete(item);
								reset();
							}
						})) {
			@Override
			public ItemDto getValue(ItemDto object) {
				return object;
			}
		};
		cellTable.addColumn(deleteColumn,
				SafeHtmlUtils.fromSafeConstant("<br/>"));
	}

	@Override
	public ColumnSortList getTableColumnSortList() {
		return cellTable.getColumnSortList();
	}

	@Override
	public void setListName(String name) {
		shoppingListText.setText(name);
	}

	@Override
	public int getShopId() {
		int index = shopList.getSelectedIndex();
		if (index == -1) {
			return -1;
		} else {
			String value = shopList.getValue(index);
			return Integer.parseInt(value);
		}
	}

	@Override
	public void setShops(List<Shop> shops) {
		for (Shop shop : shops) {
			shopList.addItem(shop.getName(), "" + shop.getId());
		}
	}

	@Override
	public void refresh() {
		ColumnSortList columnSortList = cellTable.getColumnSortList();
		Range visibleRange = cellTable.getVisibleRange();
		presenter.loadData(visibleRange, columnSortList);
	}

}