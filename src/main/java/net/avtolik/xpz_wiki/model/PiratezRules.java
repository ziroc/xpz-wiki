package net.avtolik.xpz_wiki.model;

import java.util.List;

public class PiratezRules {

	List<Item> items;
	List<Craft> crafts;
	List<Armor> armors;
	List<Research> research;
	List<Manufacture> manufacture;
	List<Article> ufopaedia;
	

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Research> getResearch() {
		return research;
	}

	public void setResearch(List<Research> research) {
		this.research = research;
	}

	public List<Article> getUfopaedia() {
		return ufopaedia;
	}

	public void setUfopaedia(List<Article> ufopaedia) {
		this.ufopaedia = ufopaedia;
	}

	public List<Armor> getArmors() {
		return armors;
	}

	public void setArmors(List<Armor> armors) {
		this.armors = armors;
	}
	
	public List<Manufacture> getManifacture() {
		return manufacture;
	}

	public void setManufacture(List<Manufacture> manifacture) {
		this.manufacture = manifacture;
	}

	public List<Craft> getCrafts() {
		return crafts;
	}

	public void setCrafts(List<Craft> crafts) {
		this.crafts = crafts;
	}

}
