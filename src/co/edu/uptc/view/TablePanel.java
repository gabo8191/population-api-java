package co.edu.uptc.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class TablePanel extends JPanel {

    private JTable table;
    private JButton cakeButton, barButton;

    public TablePanel(List<String> years, List<String> quantities) {
        this.setLayout(new GridBagLayout());
        initializeComponents(years, quantities);
    }

    private void initializeComponents(List<String> years, List<String> quantities) {
        JScrollPane scrollPane = initializeTable(years, quantities);
        setTableFeatures(years);
        setTableGridFeatures(scrollPane);

        cakeButton = new JButton("Gráfica de pastel");
        cakeButton.setActionCommand("Cake");
        cakeButton.setBorderPainted(false);
        cakeButton.setBackground(Color.WHITE);

        barButton = new JButton("Gráfica de barras");
        barButton.setActionCommand("Bar");
        barButton.setBorderPainted(false);
        barButton.setBackground(Color.WHITE);

        setCakeButtonFeatures();
        setBarButtonFeatures();
    }

    private JScrollPane initializeTable(List<String> years, List<String> quantities) {
        DefaultTableModel tableModel = new DefaultTableModel(0, 2);

        tableModel.setColumnIdentifiers(new Object[] { "Año", "Cantidad" });

        table = new JTable(tableModel);

        long total = 0;
        for (int i = 0; i < years.size(); i++) {
            total += Long.parseLong(quantities.get(i));
            tableModel.addRow(new Object[] { years.get(i), quantities.get(i) });
        }

        tableModel.addRow(new Object[] { "Total", total });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        return scrollPane;
    }

    private void setTableFeatures(List<String> years) {
        for (String year : years) {
            table.getColumnModel().getColumn(0).setPreferredWidth(year.length() * 10);
        }
    }

    private void setTableGridFeatures(JScrollPane scrollPane) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        add(scrollPane, constraints);
    }

    private void setCakeButtonFeatures() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(cakeButton, gbc);
    }

    private void setBarButtonFeatures() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(barButton, gbc);
    }

    public void setActionListener(ActionListener listener) {
        cakeButton.addActionListener(listener);
        barButton.addActionListener(listener);
    }

    public void updateData(List<String> years, List<String> quantities) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);

        long total = 0;
        for (int i = 0; i < years.size(); i++) {
            total += Long.parseLong(quantities.get(i));
            tableModel.addRow(new Object[] { years.get(i), quantities.get(i) });
        }

        tableModel.addRow(new Object[] { "Total", total });
    }
}
