package net.avtolik.xpz_wiki.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Game Item
 *
 */
public class Item {

	String delete;
	String name; // id actually
	List<String> categories;
	String realName;
	String description;

	int weight;
	
	Integer accuracyMelee;
	Integer damageType;
	Integer power;
	Integer clipSize;
	
	Integer tuAuto;
	Integer tuSnap;
	Integer tuAimed;
	
	Integer accuracyAuto;
	Integer accuracySnap;
	Integer accuracyAimed;
	Map<String, MultipleString> accuracyMultiplier;
	
	Integer autoRange;
	Integer snapRange;
	Integer aimRange;
	
	boolean flatRate;
	
	DamageAlter damageAlter;
	

	String armorName;
	Cost costSnap;
	Cost costMelee;
	Cost costAimed;
	Cost costThrow;

	Map<String, Integer> costUse = new HashMap<>();
	

	Integer shotgunPellets;
	
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
	public Integer getTuAuto() {
		return tuAuto;
	}
	public Integer getTuSnap() {
		return tuSnap;
	}
	public Integer getTuAimed() {
		return tuAimed;
	}
	public void setTuAuto(Integer tuAuto) {
		this.tuAuto = tuAuto;
	}
	public void setTuSnap(Integer tuSnap) {
		this.tuSnap = tuSnap;
	}
	public void setTuAimed(Integer tuAimed) {
		this.tuAimed = tuAimed;
	}
	public Integer getAutoRange() {
		return autoRange;
	}
	public Integer getSnapRange() {
		return snapRange;
	}
	public Integer getAimRange() {
		return aimRange;
	}
	public void setAutoRange(Integer autoRange) {
		this.autoRange = autoRange;
	}
	public void setSnapRange(Integer snapRange) {
		this.snapRange = snapRange;
	}
	public void setAimRange(Integer aimRange) {
		this.aimRange = aimRange;
	}
	public Integer getAccuracyAuto() {
		return accuracyAuto;
	}
	public Integer getAccuracySnap() {
		return accuracySnap;
	}
	public Integer getAccuracyAimed() {
		return accuracyAimed;
	}
	public void setAccuracyAuto(Integer accuracyAuto) {
		this.accuracyAuto = accuracyAuto;
	}
	public void setAccuracySnap(Integer accuracySnap) {
		this.accuracySnap = accuracySnap;
	}
	public void setAccuracyAimed(Integer accuracyAimed) {
		this.accuracyAimed = accuracyAimed;
	}
	public String getArmorName() {
		return armorName;
	}
	public void setArmorName(String armorName) {
		this.armorName = armorName;
	}
	public DamageAlter getDamageAlter() {
		return damageAlter;
	}
	public void setDamageAlter(DamageAlter damageAlter) {
		this.damageAlter = damageAlter;
	}
	public Integer getClipSize() {
		return clipSize;
	}
	public void setClipSize(Integer clipSize) {
		this.clipSize = clipSize;
	}
	public boolean isFlatRate() {
		return flatRate;
	}
	public void setFlatRate(boolean flatRate) {
		this.flatRate = flatRate;
	}
	public Cost getCostSnap() {
		return costSnap;
	}
	public void setCostSnap(Cost costSnap) {
		this.costSnap = costSnap;
	}
	public Cost getCostAimed() {
		return costAimed;
	}
	public void setCostAimed(Cost costAimed) {
		this.costAimed = costAimed;
	}
	public Cost getCostMelee() {
		return costMelee;
	}
	public void setCostMelee(Cost costMelee) {
		this.costMelee = costMelee;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public Integer getShotgunPellets() {
		return shotgunPellets;
	}
	public void setShotgunPellets(Integer shotgunPellets) {
		this.shotgunPellets = shotgunPellets;
	}
	public Cost getCostThrow() {
		return costThrow;
	}
	public void setCostThrow(Cost costThrow) {
		this.costThrow = costThrow;
	}
	public Map<String, Integer> getCostUse() {
		return costUse;
	}
	public void setCostUse(Map<String, Integer> costUse) {
		this.costUse = costUse;
	}
	public Map<String, MultipleString> getAccuracyMultiplier() {
		return accuracyMultiplier;
	}
	public void setAccuracyMultiplier(Map<String, MultipleString> accuracyMultiplier) {
		this.accuracyMultiplier = accuracyMultiplier;
	}
	

}
