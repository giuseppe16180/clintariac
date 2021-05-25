package com.clintariac.components.reservationsList;

import java.awt.Component;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import com.clintariac.components.mvc.View;
import com.clintariac.components.reservationsList.reservation.ReservationModel;
import com.clintariac.components.reservationsList.reservation.ReservationRenderer;
import com.clintariac.services.utils.AppUtils;

public class ReservationsListView implements View {

    private JScrollPane mainPanel;
    private JList<ReservationModel> list;

    public ReservationsListView() {

        mainPanel = new JScrollPane();
        mainPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setBackground(null);

        mainPanel.setBorder(AppUtils.createMainBorder("Appuntamenti"));

        list = new JList<>();
        list.setCellRenderer(new ReservationRenderer());
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        mainPanel.setViewportView(list);
    }


    /**
     * @return Component
     */
    @Override
    public Component getMainComponent() {
        return this.mainPanel;
    }


    /**
     * @return JList<ReservationModel>
     */
    public JList<ReservationModel> getList() {
        return list;
    }
}
