package eu.zeigermann.gwt.demo.shared.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

@SuppressWarnings("serial")
@Entity
@NamedQueries({ @NamedQuery(name = Shop.QUERY_ALL, query = "SELECT o FROM " + Shop.TABLE + " o") })
@Table(name=Shop.TABLE)
public class Shop implements Serializable {
	public final static String TABLE = "Shop";
	public final static String QUERY_ALL = TABLE + ".all";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	private String name;
	
	@Version
	int version;

	public Shop() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
