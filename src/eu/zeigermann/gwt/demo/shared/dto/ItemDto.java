package eu.zeigermann.gwt.demo.shared.dto;


@SuppressWarnings("serial")
public class ItemDto extends AbstractBaseDto {
	public boolean checked;
	public int listId = -1;
	public int shopId = -1;
	public String shopName;
	public int position = -1;

	// getters / setters to make Dozer happy
	
	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public ItemDto() {
	}
	
	public ItemDto(String name) {
		this.name = name;
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

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
