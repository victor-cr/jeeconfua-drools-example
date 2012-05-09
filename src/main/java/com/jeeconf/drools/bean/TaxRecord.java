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
    private final Party party;
    private final BigDecimal amount;

    public TaxRecord(Party party, BigDecimal amount) {
        this.party = party;
        this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Party getParty() {
        return party;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isSelling() {
        return amount != null && BigDecimal.ZERO.compareTo(amount) < 0;
    }

    public boolean isPurchasing() {
        return amount != null && BigDecimal.ZERO.compareTo(amount) > 0;
    }
}
