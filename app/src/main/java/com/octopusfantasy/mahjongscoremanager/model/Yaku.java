package com.octopusfantasy.mahjongscoremanager.model;

public class Yaku {

    private int han;
    private String name;
    private Boolean closedOnly;
    private int openedHan;

    public Yaku(int han, String name, boolean closedOnly) {
        this(han, name, closedOnly, 0);
    }

    public Yaku(int han, String name, Boolean closedOnly, int openedHan) {
        this.han = han;
        this.name = name;
        this.closedOnly = closedOnly;
        this.openedHan = openedHan;
    }

    public int getHan() {
        return han;
    }

    public String getName() {
        return name;
    }

    public Boolean getClosedOnly() {
        return closedOnly;
    }

    public int getOpenedHan() {
        return openedHan;
    }

}
