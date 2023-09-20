package net.avtolik.xpz_wiki.model;

import java.util.HashSet;
import java.util.List;

public class UsedForObject {
    private boolean notEmpty = false;

    private List<String> nameList;
    private HashSet<String> idList;

    public HashSet<String> getIdList() {
        return idList;
    }
    public void setIdList(HashSet<String> idList) {
        this.idList = idList;
    }
    public boolean isNotEmpty() {
        return notEmpty;
    }
    public void setNotEmpty(boolean notEmpty) {
        this.notEmpty = notEmpty;
    }

    public List<String> getNameList() {
        return nameList;
    }
    public void setNameList(List<String> name) {
        this.nameList = name;
    }

    
}
