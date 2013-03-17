package eu.zeigermann.gwt.demo.shared.entity.objectify;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;

@SuppressWarnings("serial")
@Entity
public class ShoppingList extends AbstractShoppingEntity {
	public final static String TABLE = "ShoppingList";
	public final static String QUERY_ALL = TABLE + ".all";

//	@OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
//	@OrderBy("position")
	List<Item> items = new ArrayList<Item>();

	public List<Item> getItems() {
		return items;
	}

	public void addItem(Item item) {
		items.add(item);
		item.setList(this);
		item.setPosition(items.size());
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
//	@PreUpdate
	public void refreshPositions() {
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			item.setPosition(i + 1);
		}
		
	}

}
