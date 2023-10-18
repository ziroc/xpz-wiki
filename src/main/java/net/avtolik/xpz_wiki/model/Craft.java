package net.avtolik.xpz_wiki.model;

import java.util.List;

public class Craft {
    String name; // id actually
    String realName;
    List<String> requires;
    String description;
    Integer fuelMax;
    Integer damageMax;
    String shieldCapacity;
    Integer speedMax;
    Integer accel;
    Integer weapons;
    Integer pilots;
    Integer soldiers;
    String vehicles;
    Integer maxLargeUnits;
    Integer refuelRate;
    Integer radarRange;
    Integer radarChance;
    Integer repairRate;
    String refuelItem;
    String costBuy;
    
    public Craft () {

    }
    public Craft (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public Integer getFuelMax() {
        return fuelMax;
    }
    public void setFuelMax(Integer fuelMax) {
        this.fuelMax = fuelMax;
    }
    public Integer getDamageMax() {
        return damageMax;
    }
    public void setDamageMax(Integer damageMax) {
        this.damageMax = damageMax;
    }
    public Integer getSpeedMax() {
        return speedMax;
    }
    public void setSpeedMax(Integer speedMax) {
        this.speedMax = speedMax;
    }
    public Integer getAccel() {
        return accel;
    }
    public void setAccel(Integer accel) {
        this.accel = accel;
    }
    public Integer getWeapons() {
        return weapons;
    }
    public void setWeapons(Integer weapons) {
        this.weapons = weapons;
    }
    public Integer getPilots() {
        return pilots;
    }
    public void setPilots(Integer pilots) {
        this.pilots = pilots;
    }
    public Integer getSoldiers() {
        return soldiers;
    }
    public void setSoldiers(Integer soldiers) {
        this.soldiers = soldiers;
    }
    public String getVehicles() {
        return vehicles;
    }
    public void setVehicles(String vehicles) {
        this.vehicles = vehicles;
    }
    public Integer getMaxLargeUnits() {
        return maxLargeUnits;
    }
    public void setMaxLargeUnits(Integer maxLargeUnits) {
        this.maxLargeUnits = maxLargeUnits;
    }
    public Integer getRefuelRate() {
        return refuelRate;
    }
    public void setRefuelRate(Integer refuelRate) {
        this.refuelRate = refuelRate;
    }
    public Integer getRadarRange() {
        return radarRange;
    }
    public void setRadarRange(Integer radarRange) {
        this.radarRange = radarRange;
    }
    public Integer getRadarChance() {
        return radarChance;
    }
    public void setRadarChance(Integer radarChance) {
        this.radarChance = radarChance;
    }
    public Integer getRepairRate() {
        return repairRate;
    }
    public void setRepairRate(Integer repairRate) {
        this.repairRate = repairRate;
    }
    public String getRefuelItem() {
        return refuelItem;
    }
    public void setRefuelItem(String refuelItem) {
        this.refuelItem = refuelItem;
    }
    public String getShieldCapacity() {
        return shieldCapacity;
    }
    public void setShieldCapacity(String shieldCapacity) {
        this.shieldCapacity = shieldCapacity;
    }
    public String getCostBuy() {
        return costBuy;
    }
    public void setCostBuy(String costBuy) {
        this.costBuy = costBuy;
    }
    public List<String> getRequires() {
        return requires;
    }
    public void setRequires(List<String> requires) {
        this.requires = requires;
    }

    

}
