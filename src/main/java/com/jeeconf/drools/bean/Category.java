package com.jeeconf.drools.bean;

/**
 * Taxpayer category.
 *
 * @author Victor Polischuk
 */
public enum Category {
    I("I"),
    II("II"),
    III("III"),
    NONE("<none>");

    private final String description;

    private Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
