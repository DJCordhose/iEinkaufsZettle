package eu.zeigermann.gwt.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;

import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundary;
import eu.zeigermann.gwt.demo.shared.boundary.ShoppingBoundaryAsync;
import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public class iEinkaufsZettle implements EntryPoint {
	
	private ShoppingBoundaryAsync service = GWT.create(ShoppingBoundary.class); 
	
	public void onModuleLoad() {
		FlowPanel flowPanel = new FlowPanel();
		final TextBox name = new TextBox();
		flowPanel.add(name);
		Button button = new Button("Create");
		button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				service.createList(name.getText(), new AsyncCallback<ShoppingList>() {
					
					@Override
					public void onSuccess(ShoppingList list) {
						name.setText(list.getName());
					}
					
					@Override
					public void onFailure(Throwable caught) {
						GWT.log("Error: "+ caught);
					}
				});
			}
		});
		
		flowPanel.add(button);
		RootLayoutPanel.get().add(flowPanel);
	}
}
