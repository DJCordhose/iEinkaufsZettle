package eu.zeigermann.gwt.demo.shared.entity.objectify;

import com.googlecode.objectify.annotation.Entity;

@SuppressWarnings("serial")
@Entity
public class Item extends AbstractShoppingEntity {

	boolean checked;

//	@ManyToOne
	ShoppingList list;

//	@ManyToOne
	Shop shop;
	
	int position;

	public Item() {
	}
	
	public Item(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setList(ShoppingList list) {
		this.list = list;
	}
	
	public ShoppingList getList() {
		return list;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
