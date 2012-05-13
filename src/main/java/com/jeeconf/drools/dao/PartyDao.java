package com.jeeconf.drools.dao;

import com.jeeconf.drools.bean.Category;
import com.jeeconf.drools.bean.Company;
import com.jeeconf.drools.bean.Entrepreneur;
import com.jeeconf.drools.bean.Party;
import com.jeeconf.drools.bean.Person;
import com.jeeconf.drools.bean.Taxpayer;

import java.util.List;

/**
 * Party DAO interface
 *
 * @author Victor Polischuk
 */
public interface PartyDao {
    List<Party> findAll();

    List<Company> findAllCompanies();

    List<Entrepreneur> findAllEntrepreneurs();

    List<Taxpayer> findPartyByCategory(Category category);

    Company findCompany(String name);

    Entrepreneur findEntrepreneur(String name);

    Person findPerson();

    void addCompany(Company company);

    void addEntrepreneur(Entrepreneur entrepreneur);
}
