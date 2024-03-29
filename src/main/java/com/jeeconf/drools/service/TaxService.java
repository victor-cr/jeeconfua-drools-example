package com.jeeconf.drools.service;

import com.jeeconf.drools.bean.Company;
import com.jeeconf.drools.bean.Entrepreneur;
import com.jeeconf.drools.bean.Party;
import com.jeeconf.drools.bean.Person;
import com.jeeconf.drools.bean.TaxRecord;
import com.jeeconf.drools.bean.TotalRecord;
import com.jeeconf.drools.bean.TransactionRecord;
import com.jeeconf.drools.dao.PartyDao;
import com.jeeconf.drools.dao.TransactionDao;
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
    private TransactionDao transactionDao;
    private KnowledgeBase knowledgeBase;

    @Required
    public void setPartyDao(PartyDao partyDao) {
        this.partyDao = partyDao;
    }

    @Required
    public void setTransactionDao(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
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
        return transactionDao.findAll();
    }

    public void addTransaction(Party party, Party counterParty, BigDecimal amount) {
        Person person = partyDao.findPerson();

        if (person.equals(counterParty)) {
            transactionDao.addRecord(new TransactionRecord(party, counterParty, amount));
        } else {
            transactionDao.addRecord(new TransactionRecord(party, counterParty, amount));
            transactionDao.addRecord(new TransactionRecord(counterParty, party, amount.negate()));
        }
    }

    public Result calculate() {
        StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();

        try {
            session.setGlobal("dao", transactionDao);
            session.setGlobal("log", LOG);

            List<Party> parties = partyDao.findAll();

            for (Party object : parties) {
                LOG.debug("Insert fact: " + object);
                session.insert(object);
            }

            LOG.info("Fire all rules");

            session.fireAllRules();

            Collection taxRecords = session.getObjects(new ClassObjectFilter(TaxRecord.class));
            Collection totalRecords = session.getObjects(new ClassObjectFilter(TotalRecord.class));

            return new Result(taxRecords, totalRecords);
        } finally {
            session.dispose();
        }
    }

    public static class Result {
        private final List<TaxRecord> taxRecordList;
        private final List<TotalRecord> totalRecordList;

        @SuppressWarnings("unchecked")
        public Result(Collection taxRecordList, Collection totalRecordList) {
            this.taxRecordList = new ArrayList<TaxRecord>(taxRecordList);
            this.totalRecordList = new ArrayList<TotalRecord>(totalRecordList);
        }

        public List<TaxRecord> getTaxRecordList() {
            return taxRecordList;
        }

        public List<TotalRecord> getTotalRecordList() {
            return totalRecordList;
        }
    }
}
