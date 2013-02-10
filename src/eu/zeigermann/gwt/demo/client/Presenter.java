package eu.zeigermann.gwt.demo.client;

import com.google.gwt.user.client.ui.HasWidgets;

public interface Presenter<V> {
	void go(final HasWidgets container);
}
