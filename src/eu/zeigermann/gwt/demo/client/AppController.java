package eu.zeigermann.gwt.demo.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import eu.zeigermann.gwt.demo.client.event.EditItemsEvent;
import eu.zeigermann.gwt.demo.client.event.EditItemsEventHandler;
import eu.zeigermann.gwt.demo.shared.entity.ShoppingList;

public class AppController implements Presenter, ValueChangeHandler<String> {
  private final HandlerManager eventBus;
  private HasWidgets container;
  
  public AppController(HandlerManager eventBus) {
    this.eventBus = eventBus;
    bind();
  }
  
  private void bind() {
    History.addValueChangeHandler(this);

    eventBus.addHandler(EditItemsEvent.TYPE,
        new EditItemsEventHandler() {

			@Override
			public void onEditContact(EditItemsEvent event) {
				doEditItems(event.getList(), true);
			}
        });  
  }

	private void doEditItems(ShoppingList list, boolean createHistory) {
		if (createHistory) {
		    History.newItem("editList:"+list.getId(), false);
		}
	}

	private void doEditLists(boolean createHistory) {
		if (createHistory) {
			History.newItem("editLists", false);
		}
	    MainPresenter mainPresenter = new MainPresenter();
	    MainView view = new UIBinderMainView(mainPresenter.getDataProvider());
	    mainPresenter.setEventBus(eventBus);
	    mainPresenter.setView(view);
	    mainPresenter.go(container);
	}

  public void go(final HasWidgets container) {
    this.container = container;
    
    if ("".equals(History.getToken())) {
      History.newItem("editLists");
    }
    else {
      History.fireCurrentHistoryState();
    }
  }

  public void onValueChange(ValueChangeEvent<String> event) {
    String token = event.getValue();
    
    if (token != null) {
      if (token.equals("editLists")) {
    	  doEditLists(false);
      }
      else if (token.startsWith("editList:")) {
    	  String[] split = token.split(":");
//    	  doEditItems()
      }
    }
  } 
}
