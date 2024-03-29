package com.jeeconf.drools.bean;

import java.util.Collections;
import java.util.List;

/**
 * A person who shall be treated as a one of the people.
 *
 * @author Victor Polischuk
 */
public final class Person implements Party {
    private static final String NAME = "People";

    private Person() {
    }

    public static Person getInstance() {
        return Singleton.INSTANCE;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<Role> getSpecialRoles() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return getName();
    }

    private static class Singleton {
        private static final Person INSTANCE = new Person();
    }
}
