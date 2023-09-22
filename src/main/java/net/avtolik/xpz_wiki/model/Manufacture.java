package net.avtolik.xpz_wiki.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manifacture Item
 *
 */
public class Manufacture {

	String delete;
	String name; // id actually
	Integer time;
	Integer cost;
	
	private List<String> requires;     // research name
	private Map<String, Object> requiredItems = new HashMap<>();
	

	@Override
	public String toString() {
		return "Manufacture [delete=" + delete + ", name=" + name + ", requires=" + requires + ", requiredItems="
				+ requiredItems + "]";
	}
	public Map<String, Object> getRequiredItems() {
		return requiredItems;
	}
	public void setRequiredItems(Map<String, Object> requiredItems) {
		this.requiredItems = requiredItems;
	}
	public List<String> getRequires() {
		return requires;
	}
	public void setRequires(List<String> requires) {
		this.requires = requires;
	}
	public String getDelete() {
		return delete;
	}
	public String getName() {
		return name;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	
}
