package com.geccocrawler.gecco.demo.weather;

public enum CharacterEnum {
    UTF8("UTF-8");

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    CharacterEnum(String name) {
        this.name = name;
    }
}
