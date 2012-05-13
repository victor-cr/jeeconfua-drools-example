package com.jeeconf.drools.ui;

import com.jeeconf.drools.bean.TaxRecord;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Revenue records table model.
 *
 * @author Victor Polischuk
 */
public class TaxTableModel extends AbstractTableModel {
    private static final Class[] COLUMN_CLASSES = {String.class, String.class, BigDecimal.class};
    private static final String[] COLUMN_NAMES = {"Party", "Category", "Tax"};

    private List<TaxRecord> records;

    public TaxTableModel() {
        this.records = new ArrayList<TaxRecord>();
    }

    public void refresh(List<TaxRecord> records) {
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
        TaxRecord taxRecord = records.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return taxRecord.getParty().getName();
            case 1:
                return taxRecord.isCorrection() ? "<correction>" : taxRecord.getParty().getCategory().getDescription();
            case 2:
                return taxRecord.getAmount();
        }

        throw new IllegalArgumentException("Given columnIndex is invalid");
    }
}
