package com.clintariac.components.reservationsList;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import com.clintariac.components.reservationsList.reservation.ReservationModel;

public class ReservationsListModel {

    private ListModel<ReservationModel> reservations;

    public ReservationsListModel(List<ReservationModel> reservations) {
        DefaultListModel<ReservationModel> res = new DefaultListModel<ReservationModel>();
        res.addAll(reservations);
        this.reservations = res;
    }


    /**
     * @return ListModel<ReservationModel>
     */
    public ListModel<ReservationModel> getReservations() {
        return reservations;
    }


    /**
     * @param reservations
     */
    public void setReservations(ListModel<ReservationModel> reservations) {
        this.reservations = reservations;
    }
}
