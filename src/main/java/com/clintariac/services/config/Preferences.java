
package com.clintariac.services.config;

import java.time.LocalTime;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

public interface Preferences {
    static final String subject = "PRENOTAZIONE";
    static final TimeIncrement examDuration = TimeIncrement.TwentyMinutes;
    static final LocalTime openingTime = LocalTime.parse("07:00");
    static final LocalTime closingTime = LocalTime.parse("19:00");
}
