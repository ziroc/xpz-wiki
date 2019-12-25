package net.avtolik.xpz_wiki.model.saveFile;

import java.util.List;

public class Bases {

	private String name;
	List<CurrentResearch> research;

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
	
}
