package com.jeeconf.drools.ui;

import com.jeeconf.drools.bean.TotalRecord;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Total records table model.
 *
 * @author Victor Polischuk
 */
public class TotalTableModel extends AbstractTableModel {
    private static final Class[] COLUMN_CLASSES = {String.class, BigDecimal.class, BigDecimal.class};
    private static final String[] COLUMN_NAMES = {"Party", "Transaction Total", "Tax Total"};

    private List<TotalRecord> records;

    public TotalTableModel() {
        this.records = new ArrayList<TotalRecord>();
    }

    public void refresh(List<TotalRecord> records) {
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
        TotalRecord transactionRecord = records.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return transactionRecord.getParty().getName();
            case 1:
                return transactionRecord.getTransactionAmount();
            case 2:
                return transactionRecord.getTaxAmount();
        }

        throw new IllegalArgumentException("Given columnIndex is invalid");
    }
}
