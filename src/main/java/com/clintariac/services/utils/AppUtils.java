package com.clintariac.services.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Raccolta di metodi statici di utilità per l'applicazione.
 */

public interface AppUtils {

    /**
     * Converte un {@code LocalDateTime} in una stringa, secondo una particolare formattazione.
     * 
     * @param LocalDateTime
     * @return String
     */
    public static String localDateTimeToString(LocalDateTime dateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String s = dateTime.truncatedTo(ChronoUnit.MINUTES).format(formatter);
        return s;
    }

    /**
     * Converte del testo in HTML al fine di introdurre dei break per spezzare il testo in più
     * righe.
     * 
     * @param String il testo di partenza
     * @param int il numero di parole per riga
     * @return String
     */
    public static String plainTextToHTML(String text, int wordsInRow) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            builder.append(words[i]);
            builder.append((i % wordsInRow == 0) && i != 0 ? "<br>" : " ");
        }
        builder.append("</html>");
        return builder.toString();
    }

    /**
     * Restituisce un bordo da aggiungere ai pannelli della UI con il testo in input.
     * 
     * @param String
     * @return TitledBorder
     */

    public static TitledBorder createMainBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), title,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, AppUtils.title, null);
    }


    /**
     * Restituisce un bordo da aggiungere ai pannelli della UI con il testo in input.
     * 
     * @param String
     * @return TitledBorder
     */

    public static TitledBorder createSimpleBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), title,
                TitledBorder.RIGHT,
                TitledBorder.ABOVE_BOTTOM, AppUtils.textSmall, null);
    }


    /**
     * Restituisce un bordo da aggiungere ai pannelli della UI con il testo in input.
     * 
     * @param String
     * @return TitledBorder
     */

    public static TitledBorder createSimpleBorderTop(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), title,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.TOP, AppUtils.text, null);
    }

    /**
     * Restituisce un bordo da aggiungere ai pannelli della UI con il testo in input.
     * 
     * @param String
     * @return TitledBorder
     */

    public static TitledBorder smallBorderLeft(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), title,
                TitledBorder.LEFT,
                TitledBorder.BELOW_BOTTOM, AppUtils.textSmaller, null);
    }

    /**
     * Restituisce un bordo da aggiungere ai pannelli della UI con il testo in input.
     * 
     * @param String
     * @return TitledBorder
     */

    public static TitledBorder smallBorderRight(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), title,
                TitledBorder.RIGHT,
                TitledBorder.BELOW_BOTTOM, AppUtils.textSmaller, null);
    }

    /**
     * Tipi di font usati nell'applicazione.
     */

    public static Font title = new Font("Monospaced", Font.PLAIN, 20);
    public static Font text = new Font("Monospaced", Font.PLAIN, 16);
    public static Font textSmall = new Font("Monospaced", Font.PLAIN, 13);
    public static Font textSmaller = new Font("Monospaced", Font.PLAIN, 10);
}
