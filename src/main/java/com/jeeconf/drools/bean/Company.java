package com.jeeconf.drools.bean;

import java.util.List;

/**
 * A party which does not physically exist
 *
 * @author Victor Polischuk
 */
public class Company extends Taxpayer implements Party {
    public Company(String name) {
        super(name);
    }

    public Company(String name, List<Role> specialRoles) {
        super(name, specialRoles);
    }

    @Override
    public Category getCategory() {
        return Category.NONE;
    }
}
