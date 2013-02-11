package eu.zeigermann.gwt.demo.shared.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@NamedQueries({
	@NamedQuery(name = Item.QUERY_ALL, query = "SELECT o FROM " + Item.TABLE + " o"),
	@NamedQuery(name = Item.QUERY_COUNT_ITEMS, query = "SELECT count(o) FROM " + Item.TABLE + " o where o.list = :list")
	})
@Table(name=Item.TABLE)
public class Item extends AbstractShoppingEntity {
	public final static String TABLE = "Item";
	public final static String QUERY_ALL = TABLE + ".all";
	public final static String QUERY_COUNT_ITEMS = TABLE + ".cntItems";

	boolean checked;

	@ManyToOne
	ShoppingList list;

	@ManyToOne
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
