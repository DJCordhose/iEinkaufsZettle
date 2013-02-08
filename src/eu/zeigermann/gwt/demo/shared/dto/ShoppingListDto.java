package eu.zeigermann.gwt.demo.shared.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ShoppingListDto implements Serializable {
	public int id;
	public String name;
	public int version;

	// getters / setters to make Dozer happy
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
