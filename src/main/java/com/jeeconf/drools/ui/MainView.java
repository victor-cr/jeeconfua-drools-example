package com.jeeconf.drools.ui;

import com.jeeconf.drools.bean.Party;
import com.jeeconf.drools.service.TaxService;
import org.springframework.beans.factory.annotation.Required;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

/**
 * Main GUI form
 *
 * @author Victor Polischuk
 */
public class MainView {
    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private final TaxTableModel taxTableModel = new TaxTableModel();
    private final RevenueTableModel revenueTableModel = new RevenueTableModel();

    private TaxService taxService;

    private JComboBox partiesBox;
    private JTextField amountText;
    private JButton addButton;
    private JPanel root;
    private JComboBox counterPartiesBox;
    private JTable revenueTable;
    private JTable taxTable;

    public MainView() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Party party = (Party) partiesBox.getSelectedItem();
                Party counterParty = (Party) counterPartiesBox.getSelectedItem();

                if (party.equals(counterParty)) {
                    JOptionPane.showMessageDialog(root, "Party and counter party are the same.");
                    return;
                }

                try {
                    BigDecimal amount = new BigDecimal(amountText.getText());

                    taxService.addTransaction(party, counterParty, amount);

                    refreshData();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(root, "Cannot parse given amount");
                }
            }
        });
    }

    @Required
    public void setTaxService(TaxService taxService) {
        this.taxService = taxService;
    }

    public void show() {
        initLists();

        revenueTable.setModel(revenueTableModel);
        taxTable.setModel(taxTableModel);

        JFrame frame = new JFrame("Tax Preview");

        frame.setContentPane(this.root);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        refreshData();
    }

    private void initLists() {
        List<Party> taxpayerList = taxService.getTaxpayerList();
        List<Party> partyList = taxService.getPartyList();

        for (Party party : taxpayerList) {
            partiesBox.addItem(party);
        }

        for (Party party : partyList) {
            counterPartiesBox.addItem(party);
        }
    }

    private void refreshData() {
        revenueTableModel.refresh(taxService.getRevenueRecordList());
        taxTableModel.refresh(taxService.getTaxRecordList());
    }
}
