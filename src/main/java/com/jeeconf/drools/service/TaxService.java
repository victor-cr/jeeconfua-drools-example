package com.jeeconf.drools.service;

import com.jeeconf.drools.bean.Category;
import com.jeeconf.drools.bean.Company;
import com.jeeconf.drools.bean.Entrepreneur;
import com.jeeconf.drools.bean.Party;
import com.jeeconf.drools.bean.Person;
import com.jeeconf.drools.bean.TaxRecord;
import com.jeeconf.drools.bean.Taxpayer;
import com.jeeconf.drools.bean.TransactionRecord;
import com.jeeconf.drools.dao.PartyDao;
import com.jeeconf.drools.dao.RevenueDao;
import org.drools.ClassObjectFilter;
import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 */
public class TaxService {
    private final static Logger LOG = LoggerFactory.getLogger(TaxService.class);

    private PartyDao partyDao;
    private RevenueDao revenueDao;
    private KnowledgeBase knowledgeBase;

    @Required
    public void setPartyDao(PartyDao partyDao) {
        this.partyDao = partyDao;
    }

    @Required
    public void setRevenueDao(RevenueDao revenueDao) {
        this.revenueDao = revenueDao;
    }

    @Required
    public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }

    public List<Party> getTaxpayerList() {
        List<Company> companies = partyDao.findAllCompanies();
        List<Entrepreneur> entrepreneurs = partyDao.findAllEntrepreneurs();

        ArrayList<Party> parties = new ArrayList<Party>(companies.size() + entrepreneurs.size());

        parties.addAll(companies);
        parties.addAll(entrepreneurs);

        return parties;
    }

    public List<Party> getPartyList() {
        List<Company> companies = partyDao.findAllCompanies();
        List<Entrepreneur> entrepreneurs = partyDao.findAllEntrepreneurs();
        Person person = partyDao.findPerson();

        ArrayList<Party> parties = new ArrayList<Party>(companies.size() + entrepreneurs.size() + 1);

        parties.addAll(companies);
        parties.addAll(entrepreneurs);
        parties.add(person);

        return parties;
    }

    public List<TransactionRecord> getRevenueRecordList() {
        return revenueDao.findAll();
    }

    public void addTransaction(Party party, Party counterParty, BigDecimal amount) {
        Person person = partyDao.findPerson();

        if (person.equals(counterParty)) {
            revenueDao.addRecord(new TransactionRecord(party, counterParty, amount));
        } else {
            revenueDao.addRecord(new TransactionRecord(party, counterParty, amount));
            revenueDao.addRecord(new TransactionRecord(counterParty, party, amount.negate()));
        }
    }

    @SuppressWarnings("unchecked")
    public List<TaxRecord> getTaxRecordList() {
        StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();

        try {
            session.setGlobal("log", LOG);

            List<Taxpayer> taxpayerI = partyDao.findPartyByCategory(Category.I);
            List<Taxpayer> taxpayerII = partyDao.findPartyByCategory(Category.II);
            List<TransactionRecord> transactions = revenueDao.findAll();

            for (Taxpayer object : taxpayerI) {
                session.insert(object);
            }

            for (Taxpayer object : taxpayerII) {
                session.insert(object);
            }

            for (TransactionRecord object : transactions) {
                session.insert(object);
            }

            session.fireAllRules();

            Collection taxRecords = session.getObjects(new ClassObjectFilter(TaxRecord.class));

            return new ArrayList<TaxRecord>(taxRecords);
        } finally {
            session.dispose();
        }
    }
}