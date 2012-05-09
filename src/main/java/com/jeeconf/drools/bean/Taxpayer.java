package com.jeeconf.drools.bean;

/**
 * Abstract interface implementation
 *
 * @author Victor Polischuk
 */
public abstract class Taxpayer implements Party {
    private final Class<? extends Taxpayer> clazz;
    private final String name;

    public Taxpayer(String name) {
        this.name = name;

        this.clazz = getClass();
    }

    @Override
    public String getName() {
        return name;
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
