
package com.clintariac.services.config;

import java.time.LocalTime;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

/**
 * Raccolta di preferenze da utilizzare temporaneamente al posto dei valori impostati dal software
 * stesso.
 */

public interface Preferences {
    static final TimeIncrement examDuration = TimeIncrement.TwentyMinutes;
    static final LocalTime openingTime = LocalTime.parse("07:00");
    static final LocalTime closingTime = LocalTime.parse("19:00");
}
