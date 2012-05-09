package com.jeeconf.drools.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * An act of goods or services have been sold or purchased. Amount sign determines the kind of the operation:
 * <ul>
 *     <li>Positive - a party sells something to a counter party</li>
 *     <li>Negative - a party buys something from a counter party</li>
 * </ul>
 *
 * @author Victor Polischuk
 */
public class TaxRecord {
    private final Taxpayer party;
    private final BigDecimal amount;

    public TaxRecord(Taxpayer party, BigDecimal amount) {
        this.party = party;
        this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Taxpayer getParty() {
        return party;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
