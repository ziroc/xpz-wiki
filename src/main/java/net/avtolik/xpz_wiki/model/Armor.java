package net.avtolik.xpz_wiki.model;

import java.util.List;
import java.util.Map;

public class Armor {

	String name;
	String realName;
	String description;
	Integer weight;
	Integer personalLight;
	Map <String, Integer> stats;
	Integer visibilityAtDark;
	Integer frontArmor;
	Integer sideArmor;
	Integer rearArmor;
	Integer underArmor;
	
	List<Double> damageModifier;
	String specialWeapon;
	
	String storeItem;
	
	private List<String> units;
	
	public String getName() {
		return name;
	}
	public Integer getWeight() {
		return weight;
	}
	public Integer getPersonalLight() {
		return personalLight;
	}
	public Map<String, Integer> getStats() {
		return stats;
	}
	public Integer getFrontArmor() {
		return frontArmor;
	}
	public Integer getSideArmor() {
		return sideArmor;
	}
	public Integer getRearArmor() {
		return rearArmor;
	}
	public Integer getUnderArmor() {
		return underArmor;
	}
	public List<Double> getDamageModifier() {
		return damageModifier;
	}
	public String getSpecialWeapon() {
		return specialWeapon;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public void setPersonalLight(Integer personalLight) {
		this.personalLight = personalLight;
	}
	public void setStats(Map<String, Integer> stats) {
		this.stats = stats;
	}
	public void setFrontArmor(Integer frontArmor) {
		this.frontArmor = frontArmor;
	}
	public void setSideArmor(Integer sideArmor) {
		this.sideArmor = sideArmor;
	}
	public void setRearArmor(Integer rearArmor) {
		this.rearArmor = rearArmor;
	}
	public void setUnderArmor(Integer underArmor) {
		this.underArmor = underArmor;
	}
	public void setDamageModifier(List<Double> damageModifier) {
		this.damageModifier = damageModifier;
	}
	public void setSpecialWeapon(String specialWeapon) {
		this.specialWeapon = specialWeapon;
	}
	public String getStoreItem() {
		return storeItem;
	}
	public void setStoreItem(String storeItem) {
		this.storeItem = storeItem;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getUnits() {
		return units;
	}
	public void setUnits(List<String> units) {
		this.units = units;
	}
	public Integer getVisibilityAtDark() {
		return visibilityAtDark;
	}
	public void setVisibilityAtDark(Integer visibilityAtDark) {
		this.visibilityAtDark = visibilityAtDark;
	}
	
}
