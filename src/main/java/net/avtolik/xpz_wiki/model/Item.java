package net.avtolik.xpz_wiki.model;

import java.util.List;
import java.util.Map;

/**
 * Game Item
 *
 */
public class Item {

	String delete;
	String name;
	String realName;
	String description;

	int weight;
	
	Integer accuracyMelee;
	Integer damageType;
	Integer power;
	Map <String, Object> damageBonus;
	private List<String> compatibleAmmo;
	
	
	public String getDelete() {
		return delete;
	}
	public String getName() {
		return name;
	}
	public String getRealName() {
		return realName;
	}
	public String getDescription() {
		return description;
	}
	public int getWeight() {
		return weight;
	}
	public Integer getAccuracyMelee() {
		return accuracyMelee;
	}
	public Integer getDamageType() {
		return damageType;
	}
	public Integer getPower() {
		return power;
	}
	public Map<String, Object> getDamageBonus() {
		return damageBonus;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public void setAccuracyMelee(Integer accuracyMelee) {
		this.accuracyMelee = accuracyMelee;
	}
	public void setDamageType(Integer damageType) {
		this.damageType = damageType;
	}
	public void setPower(Integer power) {
		this.power = power;
	}
	public void setDamageBonus(Map<String, Object> damageBonus) {
		this.damageBonus = damageBonus;
	}
	public List<String> getCompatibleAmmo() {
		return compatibleAmmo;
	}
	public void setCompatibleAmmo(List<String> compatibleAmmo) {
		this.compatibleAmmo = compatibleAmmo;
	}
	
	
	
	

}
