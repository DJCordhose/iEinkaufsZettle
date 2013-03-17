package eu.zeigermann.gwt.demo.shared.entity.objectify;

import java.io.Serializable;


import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
public abstract class AbstractShoppingEntity implements Serializable {

	@Id
	long id;

	@Index
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
