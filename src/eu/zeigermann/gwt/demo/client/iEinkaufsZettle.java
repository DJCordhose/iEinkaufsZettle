package eu.zeigermann.gwt.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class iEinkaufsZettle implements EntryPoint {
	
	public void onModuleLoad() {
		RootLayoutPanel root = RootLayoutPanel.get();
		MainPresenter presenter = new MainPresenter();
		UIBinderMainView view = new UIBinderMainView(presenter.getDataProvider());
		root.add(view);
		presenter.setView(view);
		presenter.load();
	}
}
