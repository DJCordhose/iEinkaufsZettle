package eu.zeigermann.gwt.demo.client.statistics;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;

public interface StatisticsView {
	public interface ViewHandler {
		void click(String label);
	}
	Widget asWidget();
	void show(Map<String, Integer> stats);
	void setViewHandler(ViewHandler viewHandler);
}
