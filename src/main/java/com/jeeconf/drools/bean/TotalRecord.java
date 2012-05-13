package com.jeeconf.drools.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Total record
 *
 * @author Victor Polischuk
 */
public class TotalRecord {
    private final Taxpayer party;
    private final BigDecimal transactionAmount;
    private final BigDecimal taxAmount;

    public TotalRecord(Taxpayer party, BigDecimal transactionAmount, BigDecimal taxAmount) {
        this.party = party;
        this.transactionAmount = transactionAmount.setScale(2, RoundingMode.HALF_EVEN);
        this.taxAmount = taxAmount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Taxpayer getParty() {
        return party;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
}
