package com.clintariac.components.optionBar;

import java.awt.Component;
import javax.swing.JOptionPane;
import com.clintariac.components.mvc.Controller;
import com.clintariac.services.utils.Procedure;


public class OptionBarController implements Controller {

    private OptionBarView view;

    private Procedure onReload;
    private Procedure onImport;
    private Procedure onExport;
    private Procedure onConfig;

    public OptionBarController() {
        this.view = new OptionBarView();
        onReload = Procedure.doNothing();
        onImport = Procedure.doNothing();
        onExport = Procedure.doNothing();
        onConfig = Procedure.doNothing();
        init();
    }

    /**
     * @return Component
     */
    @Override
    public Component getView() {
        return view.getMainComponent();
    }

    /**
     * @param procedure
     */
    public void addOnReload(Procedure procedure) {
        this.onReload = procedure;
    }

    /**
     * @param procedure
     */
    public void addOnImport(Procedure procedure) {
        this.onImport = procedure;
    }

    /**
     * @param procedure
     */
    public void addOnExport(Procedure procedure) {
        this.onExport = procedure;
    }

    /**
     * @param procedure
     */
    public void addOnConfiguration(Procedure procedure) {
        this.onConfig = procedure;
    }

    private void init() {
        view.getReloadButton().addActionListener(e -> onReload.run());
        view.getImportButton().addActionListener(e -> importData());
        view.getExportButton().addActionListener(e -> exportData());
        view.getConfigButton().addActionListener(e -> config());
    }

    private void importData() {
        JOptionPane.showMessageDialog(
                null,
                "Per importare una lista utenti e una lista apputamenti precedentemenete salvata,\nsostituire i file tickets.json e users.json generati dal programma con le versioni che si desidera importare",
                "",
                JOptionPane.INFORMATION_MESSAGE);
        onImport.run();
    }

    private void exportData() {
        JOptionPane.showMessageDialog(
                null,
                "Per esportare l'attuale lista utenti e lista appuntamenti,\ncopiare i file tickets.json e users.json generati dal programma",
                "",
                JOptionPane.INFORMATION_MESSAGE);
        onExport.run();
    }

    private void config() {
        JOptionPane.showMessageDialog(
                null,
                "Attualmente non disponibile",
                "Configurazione credenziali",
                JOptionPane.INFORMATION_MESSAGE);
        onConfig.run();
    }
}
