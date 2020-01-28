package net.avtolik.xpz_wiki.model.saveFile;

public class Soldier {

	private String type;
	String name;
	Stats initialStats;
	Stats currentStats;
	int rank;
	Diary diary;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Stats getInitialStats() {
		return initialStats;
	}
	public void setInitialStats(Stats initialStats) {
		this.initialStats = initialStats;
	}
	public Stats getCurrentStats() {
		return currentStats;
	}
	public void setCurrentStats(Stats currentStats) {
		this.currentStats = currentStats;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public Diary getDiary() {
		return diary;
	}
	public void setDiary(Diary diary) {
		this.diary = diary;
	}
	
}
