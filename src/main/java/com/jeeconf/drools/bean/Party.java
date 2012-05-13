package com.jeeconf.drools.bean;

import java.util.List;

/**
 * General market participant interface
 *
 * @author Victor Polischuk
 */
public interface Party {
    String getName();

    List<Role> getSpecialRoles();
}
