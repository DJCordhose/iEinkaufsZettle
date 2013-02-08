package eu.zeigermann.gwt.demo.shared.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@NamedQueries({ @NamedQuery(name = Shop.QUERY_ALL, query = "SELECT o FROM " + Shop.TABLE + " o") })
@Table(name=Shop.TABLE)
public class Shop extends AbstractShoppingEntity {
	public final static String TABLE = "Shop";
	public final static String QUERY_ALL = TABLE + ".all";
}
