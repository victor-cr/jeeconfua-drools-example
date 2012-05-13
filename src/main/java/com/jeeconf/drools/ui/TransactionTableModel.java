package com.jeeconf.drools.ui;

import com.jeeconf.drools.bean.TransactionRecord;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Transaction records table model.
 *
 * @author Victor Polischuk
 */
public class TransactionTableModel extends AbstractTableModel {
    private static final Class[] COLUMN_CLASSES = {String.class, BigDecimal.class, String.class};
    private static final String[] COLUMN_NAMES = {"Party", "Amount", "Counter Party"};

    private List<TransactionRecord> records;

    public TransactionTableModel() {
        this.records = new ArrayList<TransactionRecord>();
    }

    public void refresh(List<TransactionRecord> records) {
        this.records.clear();
        this.records.addAll(records);

        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public int getRowCount() {
        return records.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TransactionRecord transactionRecord = records.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return transactionRecord.getParty().getName();
            case 1:
                return transactionRecord.getAmount();
            case 2:
                return transactionRecord.getCounterParty().getName();
        }

        throw new IllegalArgumentException("Given columnIndex is invalid");
    }
}
