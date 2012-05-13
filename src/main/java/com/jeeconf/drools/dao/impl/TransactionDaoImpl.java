package com.jeeconf.drools.dao.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.jeeconf.drools.bean.Party;
import com.jeeconf.drools.bean.TransactionRecord;
import com.jeeconf.drools.dao.TransactionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory implementation of {@link com.jeeconf.drools.dao.TransactionDao} interface
 *
 * @author Victor Polischuk
 */
public class TransactionDaoImpl implements TransactionDao {
    private final static Logger LOG = LoggerFactory.getLogger(TransactionDaoImpl.class);

    private final List<TransactionRecord> records;

    public TransactionDaoImpl(List<TransactionRecord> records) {
        this.records = records;
    }

    @Override
    public List<TransactionRecord> findAll() {
        return records;
    }

    @Override
    public List<TransactionRecord> findRecordsByParty(final Party party) {
        LOG.debug("Method 'findRecordsByParty' is executing: " + party);

        return new ArrayList<TransactionRecord>(Collections2.filter(records, new Predicate<TransactionRecord>() {
            @Override
            public boolean apply(TransactionRecord input) {
                return party.equals(input.getParty());
            }
        }));
    }

    @Override
    public void addRecord(TransactionRecord transactionRecord) {
        records.add(transactionRecord);
    }
}
