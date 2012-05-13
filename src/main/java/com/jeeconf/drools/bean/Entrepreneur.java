package com.jeeconf.drools.bean;

import java.util.List;

/**
 * A person who directly sells goods or services to other persons or companies.
 *
 * @author Victor Polischuk
 */
public class Entrepreneur extends Taxpayer implements Party {
    private Category category;

    public Entrepreneur(String name, Category category) {
        super(name);

        this.category = category;
    }

    public Entrepreneur(String name, List<Role> specialRoles, Category category) {
        super(name, specialRoles);

        this.category = category;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
