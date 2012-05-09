package com.jeeconf.drools.dao;

import com.jeeconf.drools.bean.Party;
import com.jeeconf.drools.bean.TransactionRecord;

import java.util.List;

/**
 * Party DAO interface
 *
 * @author Victor Polischuk
 */
public interface RevenueDao {
    List<TransactionRecord> findAll();

    List<TransactionRecord> findRecordsByParty(Party party);

    void addRecord(TransactionRecord transactionRecord);
}
