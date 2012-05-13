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
    private boolean correction;

    public TaxRecord(Taxpayer party, BigDecimal amount) {
        this(party, amount, false);
    }

    public TaxRecord(Taxpayer party, BigDecimal amount, boolean correction) {
        this.party = party;
        this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
        this.correction = correction;
    }

    public Taxpayer getParty() {
        return party;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isCorrection() {
        return correction;
    }

    public void setCorrection(boolean correction) {
        this.correction = correction;
    }
}
