package com.clintariac.services.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public interface AppUtils {

    public static String localDateTimeToString(LocalDateTime dateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String s = dateTime.truncatedTo(ChronoUnit.MINUTES).format(formatter);
        return s;
    }

    public static Boolean isSameDay(long dateTime1, long dateTime2) {
        LocalDate date1 = new Timestamp(dateTime1).toLocalDateTime().toLocalDate();
        LocalDate date2 = new Timestamp(dateTime2).toLocalDateTime().toLocalDate();
        return date1.equals(date2);
    }

    public static TitledBorder createMainBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), title,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, AppUtils.title, null);
    }

    public static TitledBorder createSimpleBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), title,
                TitledBorder.RIGHT,
                TitledBorder.ABOVE_BOTTOM, AppUtils.textSmall, null);
    }

    public static Font title = new Font("Monospaced", Font.PLAIN, 20);
    public static Font text = new Font("Monospaced", Font.PLAIN, 16);
    public static Font textSmall = new Font("Monospaced", Font.PLAIN, 13);
}
