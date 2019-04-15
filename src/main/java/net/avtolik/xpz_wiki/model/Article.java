package net.avtolik.xpz_wiki.model;

import java.util.List;

public class Article {

	String delete;
	String id;
	String type_id;
	String section;
	String text;
	List<String> requires;
	
	public String getDelete() {
		return delete;
	}
	public String getId() {
		return id;
	}
	public String getType_id() {
		return type_id;
	}
	public String getSection() {
		return section;
	}
	public String getText() {
		return text;
	}
	public List<String> getRequires() {
		return requires;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setRequires(List<String> requires) {
		this.requires = requires;
	}
	@Override
	public String toString() {
		return "Article [delete=" + delete + ", id=" + id + ", type_id=" + type_id + ", section=" + section + ", text="
				+ text + ", requires=" + requires + "]";
	}
}
