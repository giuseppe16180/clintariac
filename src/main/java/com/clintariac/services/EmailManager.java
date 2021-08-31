package com.clintariac.services;

import java.util.List;
import java.util.function.Consumer;

import com.clintariac.data.EmailData;

public interface EmailManager {

    /**
     * <p>
     * Metodo per l'invio di un'email i cui dettagli sono contenuti nell'oggetto ricevuto a
     * parametro {@code EmailData}.
     * </p>
     * 
     * @param email da inviare
     */
    public boolean send(EmailData email);


    /**
     * <p>
     * Metodo per la lettura di email della casella postale, restituisce la lista di email non
     * lette.
     * </p>
     * 
     * <p>
     * Le email vengono memorizzate in una lista di {@code EmailData}.
     * </p>
     * 
     * @return List<EmailData>
     */
    public List<EmailData> pull();

    /**
     * <p>
     * Metodo per aggiungere una funzione da richiamare in caso di avvenuta eccezione del client di
     * posta elettronica.
     * </p>
     * 
     * @param onException consumer di exception che definisce cosa fare con l'eccezione sollevata.
     */
    public void addOnException(Consumer<Exception> onException);

}
