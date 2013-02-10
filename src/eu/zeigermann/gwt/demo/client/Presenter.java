package eu.zeigermann.gwt.demo.client;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public interface Presenter<V> {
	void go(final HasWidgets container);

	void setView(V view);

	void setEventBus(HandlerManager eventBus);
}
