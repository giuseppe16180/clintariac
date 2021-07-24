package com.clintariac.components.dashboard;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;
import com.clintariac.components.calendar.CalendarController;
import com.clintariac.components.details.DetailsController;
import com.clintariac.components.mvc.View;
import com.clintariac.components.optionBar.OptionBarController;
import com.clintariac.components.patients.PatientsController;
import com.clintariac.components.ticketsList.TicketsListController;
import com.clintariac.components.userList.UsersListController;
import com.clintariac.components.reservationsList.ReservationsListController;

public class DashboardView implements View {

    public JPanel mainPanel;

    private CalendarController calendarController;
    private ReservationsListController resListController;
    private DetailsController detailsController;
    private PatientsController patientsController;
    private TicketsListController ticketsListController;
    private OptionBarController optionBarController;
    private UsersListController usersListController;

    public DashboardView() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        calendarController = new CalendarController();
        resListController = new ReservationsListController();
        detailsController = new DetailsController();
        patientsController = new PatientsController();
        ticketsListController = new TicketsListController();
        optionBarController = new OptionBarController();
        usersListController = new UsersListController();

        GridBagConstraints gbc;

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.90;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.ipadx = 50;
        mainPanel.add(calendarController.getView(), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.ipadx = 250;
        gbc.ipady = 650;
        mainPanel.add(resListController.getView(), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.90;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.ipadx = 50;
        mainPanel.add(patientsController.getView(), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.ipadx = 250;
        gbc.ipady = 650;
        mainPanel.add(usersListController.getView(), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 250;
        gbc.insets = new Insets(3, 3, 3, 3);
        mainPanel.add(ticketsListController.getView(), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 0.1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        // gbc.ipadx = 5;
        gbc.insets = new Insets(3, 3, 3, 3);
        mainPanel.add(detailsController.getView(), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        // gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 3, 3, 3);
        mainPanel.add(optionBarController.getView(), gbc);

    }

    /**
     * @return Component
     */
    @Override
    public Component getMainComponent() {
        return mainPanel;
    }

    /**
     * @return CalendarController
     */
    public CalendarController getCalendarController() {
        return calendarController;
    }

    /**
     * @return ReservationsListController
     */
    public ReservationsListController getReservationsListController() {
        return resListController;
    }

    /**
     * @return DetailsController
     */
    public DetailsController getDetailsController() {
        return detailsController;
    }

    /**
     * @return PatientsController
     */
    public PatientsController getPatientsController() {
        return patientsController;
    }

    /**
     * @return TicketsListController
     */
    public TicketsListController getTicketsListController() {
        return ticketsListController;
    }

    public UsersListController getUsersListController() {
        return usersListController;
    }

    /**
     * @return OptionBarController
     */
    public OptionBarController getOptionBarController() {
        return optionBarController;
    }
}
