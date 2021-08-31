package com.clintariac.components.details;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Interfaccia realizzata per poter definire l'evento {@code changedUpdate} tramite lambda
 */

@FunctionalInterface
public interface DocumentUpdateListener extends DocumentListener {

    @Override
    default void insertUpdate(DocumentEvent e) {
        changedUpdate(e);
    }

    @Override
    default void removeUpdate(DocumentEvent e) {
        changedUpdate(e);
    }

    @Override
    void changedUpdate(DocumentEvent e);
}
