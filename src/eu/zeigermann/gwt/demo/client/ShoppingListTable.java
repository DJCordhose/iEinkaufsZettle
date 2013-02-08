package eu.zeigermann.gwt.demo.client;

import java.util.Comparator;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.dom.client.Style;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public class ShoppingListTable extends Composite {

	private CellTable<ShoppingList> cellTable;

	private MainPresenter presenter;

	public ShoppingListTable(MainPresenter presenter) {
		this.presenter = presenter;
		init(presenter.getDataProvider());
	}

	private void init(ListDataProvider<ShoppingList> dataProvider) {
		final ProvidesKey<ShoppingList> keyProvider = new ProvidesKey<ShoppingList>() {
			public Object getKey(ShoppingList list) {
				return list == null ? null : list.getId();
			}
		};
		cellTable = new CellTable<ShoppingList>(keyProvider);
		dataProvider.addDataDisplay(cellTable);
		cellTable.setPageSize(5);

		final MultiSelectionModel<ShoppingList> selectionModel = new MultiSelectionModel<ShoppingList>(
				keyProvider);
		cellTable.setSelectionModel(selectionModel,
				new CellPreviewEvent.Handler<ShoppingList>() {

					@Override
					public void onCellPreview(
							CellPreviewEvent<ShoppingList> event) {
						if (Event.getTypeInt(event.getNativeEvent().getType()) == Event.ONCLICK || Event.getTypeInt(event.getNativeEvent().getType()) == Event.ONDBLCLICK ) {
							ShoppingList list = event.getValue();
							presenter.editList(list);
						}
					}
				});

		// styles

		// bootstrap
		cellTable.addStyleName("table");
		cellTable.addStyleName("table-bordered");
		// makes selection invisible on every even row
		// cellTable.addStyleName("table-striped");

		// columns
		addNameColumn(dataProvider);
		addEditColumn();
		addDeleteColumn();

		initWidget(cellTable);
	}

	private void addNameColumn(ListDataProvider<ShoppingList> dataProvider) {
		TextColumn<ShoppingList> nameColumn = new TextColumn<ShoppingList>() {
			@Override
			public String getValue(ShoppingList list) {
				return list.getName();
			}
		};
		nameColumn.setSortable(true);
		cellTable.addColumn(nameColumn, "Name");
		cellTable.setColumnWidth(nameColumn, 500, Style.Unit.PX);

		ListHandler<ShoppingList> columnSortHandler = new ListHandler<ShoppingList>(
				dataProvider.getList());
		columnSortHandler.setComparator(nameColumn,
				new Comparator<ShoppingList>() {
					public int compare(ShoppingList o1, ShoppingList o2) {
						if (o1 == o2) {
							return 0;
						}

						if (o1 != null) {
							return o2 != null ? o1.getName().compareTo(
									o2.getName()) : 1;
						}
						return -1;
					}
				});
		cellTable.addColumnSortHandler(columnSortHandler);
		cellTable.getColumnSortList().push(nameColumn);
	}

	private void addEditColumn() {
		Column<ShoppingList, ShoppingList> deleteColumn = new Column<ShoppingList, ShoppingList>(
				new ActionCell<ShoppingList>(SafeHtmlUtils
						.fromSafeConstant("<i class='icon-edit'></i>"),
						new Delegate<ShoppingList>() {
							@Override
							public void execute(final ShoppingList list) {
								presenter.editList(list);
							}
						})) {
			@Override
			public ShoppingList getValue(ShoppingList object) {
				return object;
			}
		};
		cellTable.addColumn(deleteColumn,
				SafeHtmlUtils.fromSafeConstant("<br/>"));
	}

	private void addDeleteColumn() {
		Column<ShoppingList, ShoppingList> deleteColumn = new Column<ShoppingList, ShoppingList>(
				new ActionCell<ShoppingList>(SafeHtmlUtils
						.fromSafeConstant("<i class='icon-remove'></i>"),
						new Delegate<ShoppingList>() {
							@Override
							public void execute(final ShoppingList list) {
								presenter.deleteList(list);
								presenter.getView().reset();
							}
						})) {
			@Override
			public ShoppingList getValue(ShoppingList object) {
				return object;
			}
		};
		cellTable.addColumn(deleteColumn,
				SafeHtmlUtils.fromSafeConstant("<br/>"));
	}

	public CellTable<ShoppingList> getCellTable() {
		return cellTable;
	}
}
