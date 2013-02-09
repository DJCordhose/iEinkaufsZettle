package eu.zeigermann.gwt.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class iEinkaufsZettle implements EntryPoint {
	
	public void onModuleLoad() {
		RootLayoutPanel root = RootLayoutPanel.get();
	    HandlerManager eventBus = new HandlerManager(null);
	    AppController appController = new AppController(eventBus);
	    appController.go(root);
	}
}
