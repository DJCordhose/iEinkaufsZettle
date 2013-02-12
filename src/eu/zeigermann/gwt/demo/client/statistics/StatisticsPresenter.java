package eu.zeigermann.gwt.demo.client.statistics;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import eu.zeigermann.gwt.demo.client.Presenter;
import eu.zeigermann.gwt.demo.client.event.EditShopEvent;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundary;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryAsync;

public class StatisticsPresenter implements Presenter<StatisticsView>, StatisticsView.ViewHandler {
	private ShoppingBoundaryAsync service = GWT
			.create(ShoppingBoundary.class);

	final StatisticsView view;
	final HandlerManager eventBus;

	public StatisticsPresenter(StatisticsView view, HandlerManager eventBus) {
		this.view = view;
		view.setViewHandler(this);
		this.eventBus = eventBus;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		service.statistics(new AsyncCallback<Map<String,Integer>>() {
			
			@Override
			public void onSuccess(Map<String, Integer> stats) {
				view.show(stats);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error: " + caught);
			}
		});
		container.add(view.asWidget());
	}

	@Override
	public void click(String label) {
		eventBus.fireEvent(new EditShopEvent(label));
	}

}
