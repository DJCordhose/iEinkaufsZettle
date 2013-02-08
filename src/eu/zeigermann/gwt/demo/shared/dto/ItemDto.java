package eu.zeigermann.gwt.demo.shared.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ItemDto implements Serializable {
	public int id;
	public String name;
	public boolean checked;
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
