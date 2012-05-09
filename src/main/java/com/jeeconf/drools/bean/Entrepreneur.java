package com.jeeconf.drools.bean;

/**
 * A person who directly sells goods or services to other persons or companies.
 *
 * @author Victor Polischuk
 */
public class Entrepreneur extends Taxpayer implements Party {
    private final Category category;

    public Entrepreneur(String name, Category category) {
        super(name);

        this.category = category;
    }

    @Override
    public Category getCategory() {
        return category;
    }
}
