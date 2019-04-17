package net.avtolik.xpz_wiki.model;

import java.util.List;
import java.util.Map;

/**
 * Research Item
 *
 */
public class Research {

	String delete;
	String name;
	String realName;
	String description;
	int cost;
	int points;
	boolean destroyItem;
	String needItem;
	List<String> dependencies;
	List<String> unlocks;
	List<String> requiresBaseFunc;
	List<String> getOneFree;
	List<String> disables;
	String lookup;
	String spawnedItem;
	Map<String,List<String>> getOneFreeProtected;
	
	
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public boolean isDestroyItem() {
		return destroyItem;
	}
	public void setDestroyItem(boolean destroyItem) {
		this.destroyItem = destroyItem;
	}
	public String getNeedItem() {
		return needItem;
	}
	public void setNeedItem(String needItem) {
		this.needItem = needItem;
	}
	public List<String> getDependencies() {
		return dependencies;
	}
	public void setDependencies(List<String> dependencies) {
		this.dependencies = dependencies;
	}
	public List<String> getUnlocks() {
		return unlocks;
	}
	public void setUnlocks(List<String> unlocks) {
		this.unlocks = unlocks;
	}
	public List<String> getRequiresBaseFunc() {
		return requiresBaseFunc;
	}
	public void setRequiresBaseFunc(List<String> requiresBaseFunc) {
		this.requiresBaseFunc = requiresBaseFunc;
	}
	public String getLookup() {
		return lookup;
	}
	public void setLookup(String lookup) {
		this.lookup = lookup;
	}
	public List<String> getGetOneFree() {
		return getOneFree;
	}
	public void setGetOneFree(List<String> getOneFree) {
		this.getOneFree = getOneFree;
	}
	public String getSpawnedItem() {
		return spawnedItem;
	}
	public void setSpawnedItem(String spawnedItem) {
		this.spawnedItem = spawnedItem;
	}
	public List<String> getDisables() {
		return disables;
	}
	public void setDisables(List<String> disables) {
		this.disables = disables;
	}
	public Map<String, List<String>> getGetOneFreeProtected() {
		return getOneFreeProtected;
	}
	public void setGetOneFreeProtected(Map<String, List<String>> getOneFreeProtected) {
		this.getOneFreeProtected = getOneFreeProtected;
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
	@Override
	public String toString() {
		return "Item [realName=" + realName 
				+ ", destroyItem=" + destroyItem + ", needItem=" + needItem + ", dependencies=" + dependencies
				+ ", unlocks=" + unlocks + ", requiresBaseFunc=" + requiresBaseFunc + ", getOneFree=" + getOneFree
				+ ", disables=" + disables + ", lookup=" + lookup + ", spawnedItem=" + spawnedItem	 + "]";
	}

	
	
}
