package com.clintariac.data;

import java.time.LocalDateTime;
import com.clintariac.services.utils.AppUtils;

public class TicketData {

    public final String id;
    public final String user;
    public final TicketState state;
    public final LocalDateTime booking;
    public final LocalDateTime lastInteraction;
    public final String message;

    public TicketData(String id, String user, TicketState state, LocalDateTime booking,
            LocalDateTime lastInteraction,
            String message) {

        this.id = id;
        this.user = user;
        this.state = state;
        this.booking = booking;
        this.lastInteraction = lastInteraction;
        this.message = message;
    }


    /**
     * @return String
     */
    @Override
    public String toString() {
        return "TicketData [booking=" + AppUtils.localDateTimeToString(booking) + ", id=" + id
                + ", lastInteraction="
                + AppUtils.localDateTimeToString(lastInteraction) + ", message=" + message
                + ", state=" + state
                + ", user=" + user + "]";
    }
}
