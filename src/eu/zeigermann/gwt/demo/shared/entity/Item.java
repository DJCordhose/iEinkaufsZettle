package eu.zeigermann.gwt.demo.shared.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

@SuppressWarnings("serial")
@Entity
@NamedQueries({ @NamedQuery(name = Item.QUERY_ALL, query = "SELECT o FROM " + Item.TABLE + " o") })
@Table(name=Item.TABLE)
public class Item implements Serializable {
	public final static String TABLE = "Item";
	public final static String QUERY_ALL = TABLE + ".all";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	String description;

	boolean checked;

	@ManyToOne
	private ShoppingList list;

	@ManyToOne
	private Shop shop;
	
	@Version
	int version;

	public Item() {

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

}
