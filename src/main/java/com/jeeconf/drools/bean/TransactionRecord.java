package com.jeeconf.drools.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * An act of goods or services have been sold or purchased. Amount sign determines the kind of the operation:
 * <ul>
 *     <li>Positive - a party receives money from a counter party (credit)</li>
 *     <li>Negative - a party gives money to a counter party (debit)</li>
 * </ul>
 *
 * @author Victor Polischuk
 */
public class TransactionRecord {
    private final Party party;
    private final Party counterParty;
    private final BigDecimal amount;

    public TransactionRecord(Party party, Party counterParty, BigDecimal amount) {
        this.party = party;
        this.counterParty = counterParty;
        this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Party getParty() {
        return party;
    }

    public Party getCounterParty() {
        return counterParty;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isCredit() {
        return amount != null && BigDecimal.ZERO.compareTo(amount) < 0;
    }

    public boolean isDebit() {
        return amount != null && BigDecimal.ZERO.compareTo(amount) > 0;
    }

    @Override
    public String toString() {
        return party.toString() + ":" + amount;
    }
}
