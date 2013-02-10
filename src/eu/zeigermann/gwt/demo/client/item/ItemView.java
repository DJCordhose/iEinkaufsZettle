package eu.zeigermann.gwt.demo.client.item;

import java.util.List;

import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;

import eu.zeigermann.gwt.demo.shared.dto.ItemDto;

public interface ItemView {
	public interface ViewHandler {
		void init();
		void loadData(Range range, ColumnSortList sortList);
		void delete(ItemDto item);
		void save(String text);
		void edit(ItemDto item);
		void create(String name);
	}
	void setListName(String name);
	void edit(ItemDto item);
	void setViewHandler(ViewHandler handler);
	Widget asWidget();
	void setVisibleTableData(int start, List<? extends ItemDto> data);
	void setTableRowCount(int rows);
	ColumnSortList getTableColumnSortList();
	void setDataProvider(AsyncDataProvider<ItemDto> dataProvider);
	int getShopId();
}
