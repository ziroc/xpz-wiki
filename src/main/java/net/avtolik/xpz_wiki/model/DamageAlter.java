package net.avtolik.xpz_wiki.model;

public class DamageAlter {

	Double armorEffectiveness;
	Double toArmorPre;
	Double toWound;
	Double toStun;
	Double toMorale;
	
	public Double getArmorEffectiveness() {
		return armorEffectiveness;
	}
	public void setArmorEffectiveness(Double armorEffectiveness) {
		this.armorEffectiveness = armorEffectiveness;
	}
	public Double getToArmorPre() {
		return toArmorPre;
	}
	public void setToArmorPre(Double toArmorPre) {
		this.toArmorPre = toArmorPre;
	}
	public Double getToWound() {
		return toWound;
	}
	public void setToWound(Double toWound) {
		this.toWound = toWound;
	}
	public Double getToStun() {
		return toStun;
	}
	public void setToStun(Double toStun) {
		this.toStun = toStun;
	}
	public Double getToMorale() {
		return toMorale;
	}
	public void setToMorale(Double toMorale) {
		this.toMorale = toMorale;
	}
	
	@Override
	public String toString() {
		return "DamageAlter [armorEffectiveness=" + armorEffectiveness + ", toArmorPre=" + toArmorPre + ", toWound="
				+ toWound + ", toStun=" + toStun + ", toMorale=" + toMorale + "]";
	}
	
}
