package com.jeeconf.drools.bean;

/**
 * A party which does not physically exist
 *
 * @author Victor Polischuk
 */
public class Company extends Taxpayer implements Party {
    public Company(String name) {
        super(name);
    }

    @Override
    public Category getCategory() {
        return Category.NONE;
    }
}
