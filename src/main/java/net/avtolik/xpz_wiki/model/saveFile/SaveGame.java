package net.avtolik.xpz_wiki.model.saveFile;

import java.util.List;

public class SaveGame {
	List<String> discovered;
	
	private List<Bases> bases;
	
	public List<String> getDiscovered() {
		return discovered;
	}

	public void setDiscovered(List<String> discovered) {
		this.discovered = discovered;
	}

	public List<Bases> getBases() {
		return bases;
	}

	public void setBases(List<Bases> bases) {
		this.bases = bases;
	}

}
