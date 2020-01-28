package net.avtolik.xpz_wiki.model.saveFile;

import java.util.ArrayList;
import java.util.List;

import net.avtolik.xpz_wiki.model.Research;

public class SaveGame {
//	List<String> discovered;	
	private List<Base> bases;
	
//	private List<Integer> funds;
	
	
	private transient List<Research> currentResearch = new ArrayList<>();
	
	
//	public List<String> getDiscovered() {
//		return discovered;
//	}
//
//	public void setDiscovered(List<String> discovered) {
//		this.discovered = discovered;
//	}

	public List<Base> getBases() {
		return bases;
	}

	public void setBases(List<Base> bases) {
		this.bases = bases;
	}

	public List<Research> getCurrentResearch() {
		return currentResearch;
	}

	public void setCurrentResearch(List<Research> research) {
		this.currentResearch = research;
	}

//	public List<Integer> getFunds() {
//		return funds;
//	}
//
//	public void setFunds(List<Integer> funds) {
//		this.funds = funds;
//	}

}
