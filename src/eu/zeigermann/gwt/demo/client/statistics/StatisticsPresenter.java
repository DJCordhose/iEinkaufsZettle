package eu.zeigermann.gwt.demo.client.statistics;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import eu.zeigermann.gwt.demo.client.Presenter;
import eu.zeigermann.gwt.demo.client.event.EditShopEvent;
import eu.zeigermann.gwt.demo.client.statistics.flotwrapper.FlotData;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryAsync;

public class StatisticsPresenter implements Presenter<StatisticsView>, StatisticsView.ViewHandler {
	final StatisticsView view;
	final HandlerManager eventBus;
	final ShoppingBoundaryAsync service;

	public StatisticsPresenter(ShoppingBoundaryAsync service, StatisticsView view, HandlerManager eventBus) {
		this.service = service;
		this.view = view;
		view.setViewHandler(this);
		this.eventBus = eventBus;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
//		loadGwtRpc();
		loadRest();
//		loadJsonp();
		container.add(view.asWidget());
	}

	private void loadGwtRpc() {
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
	}
	
	private void loadRest() {
		String url = getRemoteAddress();
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		try {
			Request request = builder.sendRequest(null, new RequestCallback() {

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					int statusCode = response.getStatusCode();
					if (statusCode == 200) {
						String json = response.getText();
						StatisticsJsni statistics = StatisticsJsni.createInstance(json);
						FlotData flot = statistics.toFlot();
						view.show(flot);
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					GWT.log("Error: " + exception);
				}

			});
		} catch (Exception e) {
			GWT.log("Error: " + e);
		}
	}

	private String getRemoteAddress() {
		String url = GWT.getModuleBaseURL() + "statisticsrs";
//		String url = "http://127.0.0.1:8888/zettle/"+ "statisticsrs";
		return url;
	}

	private void loadJsonp() {
		String url = getRemoteAddress();
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(url,
			     new AsyncCallback<StatisticsJsni>() {

					@Override
					public void onFailure(Throwable caught) {
						GWT.log("Error: " + caught);
					}

					@Override
					public void onSuccess(StatisticsJsni statistics) {
						FlotData flot = statistics.toFlot();
						view.show(flot);
					}
		});
	}

	@Override
	public void click(String label) {
		eventBus.fireEvent(new EditShopEvent(label));
	}

}
