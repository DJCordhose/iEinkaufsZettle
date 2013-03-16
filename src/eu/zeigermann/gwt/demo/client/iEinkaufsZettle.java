package eu.zeigermann.gwt.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

public class iEinkaufsZettle implements EntryPoint {
	
	public void onModuleLoad() {
		// takes over formatting of whole page, making bootstrap menus impossible
//		RootLayoutPanel root = RootLayoutPanel.get();
		RootPanel root = RootPanel.get();
	    HandlerManager eventBus = new HandlerManager(null);
	    AppController appController = new AppController(eventBus);
	    appController.go(root);
	}
}
