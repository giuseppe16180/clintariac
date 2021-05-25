package com.clintariac.components.optionBar;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.clintariac.components.mvc.View;
import com.clintariac.services.utils.AppUtils;

public class OptionBarView implements View {

    private JPanel mainPanel;

    private JPanel spacer1;

    private JButton reloadButton;
    private JButton importButton;
    private JButton exportButton;
    private JButton configButton;

    public OptionBarView() {

        mainPanel = new JPanel(new GridBagLayout());

        mainPanel.setBorder(AppUtils.createMainBorder("Opzioni"));

        reloadButton = new JButton();
        reloadButton.setHorizontalTextPosition(0);
        reloadButton.setText("Ricarica");
        reloadButton.setFont(AppUtils.text);
        reloadButton.setMargin(new Insets(3, 3, 3, 3));

        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 3, 3, 3);
        // gbc.ipadx = 100;

        mainPanel.add(reloadButton, gbc);

        spacer1 = new JPanel();

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 30;
        gbc.ipady = 16;
        mainPanel.add(spacer1, gbc);

        importButton = new JButton();
        importButton.setHorizontalTextPosition(0);
        importButton.setText("Importa");
        importButton.setFont(AppUtils.text);
        importButton.setMargin(new Insets(3, 3, 3, 3));

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 3, 3, 3);

        mainPanel.add(importButton, gbc);

        spacer1 = new JPanel();

        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 30;
        gbc.ipady = 16;
        mainPanel.add(spacer1, gbc);

        exportButton = new JButton();
        exportButton.setHorizontalTextPosition(0);
        exportButton.setText("Esporta");
        exportButton.setFont(AppUtils.text);
        exportButton.setMargin(new Insets(3, 3, 3, 3));

        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 3, 3, 3);

        mainPanel.add(exportButton, gbc);

        spacer1 = new JPanel();

        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 30;
        gbc.ipady = 16;
        mainPanel.add(spacer1, gbc);

        configButton = new JButton();
        configButton.setHorizontalTextPosition(0);
        configButton.setText("Configura");
        configButton.setFont(AppUtils.text);
        configButton.setMargin(new Insets(3, 3, 3, 3));

        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 3, 3, 3);

        mainPanel.add(configButton, gbc);
    }


    /**
     * @return Component
     */
    @Override
    public Component getMainComponent() {
        return mainPanel;
    }


    /**
     * @return JButton
     */
    public JButton getImportButton() {
        return importButton;
    }


    /**
     * @return JButton
     */
    public JButton getExportButton() {
        return exportButton;
    }


    /**
     * @return JButton
     */
    public JButton getReloadButton() {
        return reloadButton;
    }


    /**
     * @return JButton
     */
    public JButton getConfigButton() {
        return configButton;
    }
}
