package co.edu.uptc.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class MyFrame extends JFrame {

    private TablePanel tablePanel;
    private BarPanel barPanel;
    private CakePanel cakePanel;
    private Map<String, String> countries;
    private JComboBox<String> countriesComboBox;
    private JLabel tittle;
    private String key;

    public MyFrame(List<String> years, List<String> quantities, List<String> percentages,
            Map<String, String> countries, ActionListener listener, String key) {
        setTitle("Dashboard- Población para entre los años 2010-2022");
        setSize(800, 800);
        setLayout(new GridBagLayout());
        initComponents(years, quantities, percentages, countries, listener, key);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.key = key;
        setVisible(true);
    }

    private void initComponents(List<String> years, List<String> quantities, List<String> percentages,
            Map<String, String> countries, ActionListener listener, String key) {
        addTittle(key);
        addOptions(countries, listener);
        addTable(years, quantities);
        addGraph(quantities, percentages, years);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void updateTitle(String key) {
        tittle.setText("POBLACIÓN PARA DE " + key.toUpperCase() + " PARA LOS AÑOS 2010-2022");
    }

    private void addTittle(String key) {
        JPanel tittlePanel = new JPanel();
        tittle = new JLabel(
                "POBLACIÓN PARA DE " + key.toUpperCase() + " PARA LOS AÑOS 2010-2022");
        tittle.setFont(new Font("Arial", Font.BOLD, 25));
        tittle.setForeground(Color.BLACK);
        tittle.setHorizontalAlignment(SwingConstants.CENTER);
        tittlePanel.add(tittle);
        GridBagConstraints constraints = SetTittleFeatures();
        getContentPane().add(tittlePanel, constraints);
    }

    public void addOptions(Map<String, String> countries, ActionListener listener) {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));
        JLabel countryLabel = new JLabel("País: ");
        countriesComboBox = new JComboBox<String>();
        JButton showButton = new JButton("Mostrar");
        showButton.setActionCommand("Show");
        showButton.addActionListener(listener);
        showButton.setBorderPainted(false);
        showButton.setBackground(Color.WHITE);

        for (String country : countries.keySet()) {
            countriesComboBox.addItem(country);
        }

        optionsPanel.add(countryLabel);
        optionsPanel.add(countriesComboBox);
        optionsPanel.add(showButton);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weighty = 0.2;

        getContentPane().add(optionsPanel, constraints);
    }

    public JComboBox<String> getCountriesComboBox() {
        return countriesComboBox;
    }

    public void setCountriesComboBox(JComboBox<String> countriesComboBox) {
        this.countriesComboBox = countriesComboBox;
    }

    private GridBagConstraints SetTittleFeatures() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets = new Insets(10, 10, 10, 10);
        return constraints;
    }

    private void addTable(List<String> years, List<String> quantities) {
        tablePanel = new TablePanel(years, quantities);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weighty = 0.2;

        add(tablePanel, constraints);
    }

    private void addGraph(List<String> quantities, List<String> percentages, List<String> years) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        barPanel = new BarPanel(quantities, percentages, years);
        barPanel.setPreferredSize(new Dimension(400, 400));
        getContentPane().add(barPanel, constraints);

        constraints.insets = new Insets(10, 10, 10, 10);
        cakePanel = new CakePanel(quantities, percentages, years);
        cakePanel.setPreferredSize(new Dimension(400, 400));
        constraints.gridx = 1;
        getContentPane().add(cakePanel, constraints);

        barPanel.setVisible(false);
    }

    public void setCake() {
        barPanel.setVisible(false);
        cakePanel.setVisible(true);
    }

    public void setBar() {
        cakePanel.setVisible(false);
        barPanel.setVisible(true);
    }

    public void setActionListener(ActionListener listener) {
        tablePanel.setActionListener(listener);

    }

    public void updateDataInChart() {
        barPanel.repaint();
        cakePanel.repaint();
    }

    public void updateTableData(List<String> years, List<String> quantities) {
        tablePanel.updateData(years, quantities);
    }

}