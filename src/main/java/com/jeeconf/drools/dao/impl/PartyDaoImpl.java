package com.jeeconf.drools.dao.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.jeeconf.drools.bean.Category;
import com.jeeconf.drools.bean.Company;
import com.jeeconf.drools.bean.Entrepreneur;
import com.jeeconf.drools.bean.Party;
import com.jeeconf.drools.bean.Person;
import com.jeeconf.drools.bean.Taxpayer;
import com.jeeconf.drools.dao.PartyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * In-memory implementation of {@link PartyDao} interface
 *
 * @author Victor Polischuk
 */
public class PartyDaoImpl implements PartyDao {
    private final static Logger LOG = LoggerFactory.getLogger(PartyDaoImpl.class);

    private final List<Company> companies;
    private final List<Entrepreneur> entrepreneurs;

    public PartyDaoImpl(List<Company> companies, List<Entrepreneur> entrepreneurs) {
        this.companies = companies;
        this.entrepreneurs = entrepreneurs;
    }

    @Override
    public List<Party> findAll() {
        LOG.debug("Method 'findAll' is executing");

        return Lists.newArrayList(
                Iterables.concat(
                        companies,
                        entrepreneurs,
                        Collections.singletonList(Person.getInstance())
                )
        );
    }

    @Override
    public List<Company> findAllCompanies() {
        LOG.debug("Method 'findAllCompanies' is executing");

        return companies;
    }

    @Override
    public List<Entrepreneur> findAllEntrepreneurs() {
        LOG.debug("Method 'findAllEntrepreneurs' is executing");

        return entrepreneurs;
    }

    @Override
    public List<Taxpayer> findPartyByCategory(Category category) {
        LOG.debug("Method 'findPartyByCategory' is executing: " + category);

        return Lists.newArrayList(
                Iterables.concat(
                        Iterables.filter(companies, new TaxCategoryPredicate<Company>(category)),
                        Iterables.filter(entrepreneurs, new TaxCategoryPredicate<Entrepreneur>(category))
                )
        );
    }

    @Override
    public Company findCompany(String name) {
        LOG.debug("Method 'findCompany' is executing: " + name);

        return Iterables.find(companies, new PartyNamePredicate<Company>(name));
    }

    @Override
    public Entrepreneur findEntrepreneur(String name) {
        LOG.debug("Method 'findEntrepreneur' is executing: " + name);

        return Iterables.find(entrepreneurs, new PartyNamePredicate<Entrepreneur>(name));
    }

    @Override
    public Person findPerson() {
        LOG.debug("Method 'findPerson' is executing");

        return Person.getInstance();
    }

    @Override
    public void addCompany(Company company) {
        LOG.debug("Method 'addCompany' is executing: " + company);

        boolean exists = Iterables.any(companies, new PartyNamePredicate<Company>(company.getName()));

        if (!exists) {
            companies.add(company);
        }
    }

    @Override
    public void addEntrepreneur(Entrepreneur entrepreneur) {
        LOG.debug("Method 'addEntrepreneur' is executing: " + entrepreneur);

        boolean exists = Iterables.any(entrepreneurs, new PartyNamePredicate<Entrepreneur>(entrepreneur.getName()));

        if (!exists) {
            entrepreneurs.add(entrepreneur);
        }
    }

    private static class PartyNamePredicate<T extends Party> implements Predicate<T> {
        private final String name;

        private PartyNamePredicate(String name) {
            this.name = name;
        }

        @Override
        public boolean apply(T input) {
            return input != null && name.equals(input.getName());
        }
    }

    private static class TaxCategoryPredicate<T extends Taxpayer> implements Predicate<T> {
        private final Category category;

        private TaxCategoryPredicate(Category category) {
            this.category = category;
        }

        @Override
        public boolean apply(T input) {
            return input != null && category == input.getCategory();
        }
    }
}
