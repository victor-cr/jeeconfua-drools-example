package com.jeeconf.drools.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract interface implementation
 *
 * @author Victor Polischuk
 */
public abstract class Taxpayer implements Party {
    private final Class<? extends Taxpayer> clazz;
    private final String name;
    private final List<Role> specialRoles;

    public Taxpayer(String name) {
        this(name, null);
    }

    public Taxpayer(String name, List<Role> specialRoles) {
        this.name = name;
        this.specialRoles = specialRoles != null
                ? Collections.unmodifiableList(new ArrayList<Role>(specialRoles))
                : Collections.<Role>emptyList();

        this.clazz = getClass();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Role> getSpecialRoles() {
        return specialRoles;
    }

    public abstract Category getCategory();

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Taxpayer && equals((Taxpayer) obj);
    }

    public boolean equals(Taxpayer obj) {
        return obj != null && clazz.equals(obj.clazz) && name.equals(obj.getName());
    }

    @Override
    public String toString() {
        return clazz.getSimpleName() + "[" + name + "]";
    }
}
