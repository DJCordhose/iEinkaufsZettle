package eu.zeigermann.gwt.demo.shared.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Version;

@SuppressWarnings("serial")
@Entity
@NamedQueries({ @NamedQuery(name = ShoppingList.QUERY_ALL, query = "SELECT o FROM " + ShoppingList.TABLE + " o") })
@Table(name=ShoppingList.TABLE)
public class ShoppingList implements Serializable {
	public final static String TABLE = "ShoppingList";
	public final static String QUERY_ALL = TABLE + ".all";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	@OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
	@OrderColumn
	List<Item> items = new ArrayList<Item>();

	@Version
	int version;

	public List<Item> getItems() {
		return items;
	}

	public void addItem(Item item) {
		items.add(item);
		item.setList(this);
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
