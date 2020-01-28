package net.avtolik.xpz_wiki.model.saveFile;

import java.util.List;

public class Base {

	private String name;
	List<CurrentResearch> research;
	private List<Soldier> soldiers;

	public List<CurrentResearch> getResearch() {
		return research;
	}

	public void setResearch(List<CurrentResearch> research) {
		this.research = research;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Soldier> getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(List<Soldier> soldiers) {
		this.soldiers = soldiers;
	}
	
}
