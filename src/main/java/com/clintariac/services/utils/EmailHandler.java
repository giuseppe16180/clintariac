package com.clintariac.services.utils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import com.clintariac.data.EmailData;

/**
 * Interfaccia di utilità per gestire gli handler di email
 */

public interface EmailHandler {

    /**
     * Metodo statico che restituisce una funzione identità. Utile come accumulatore di partenza
     * concatenare gli handler di email mediante un {@code reduce}.
     * 
     * @return {Function<Optional<EmailData>, Optional<EmailData>>}
     */
    static Function<Optional<EmailData>, Optional<EmailData>> identity() {
        return t -> t;
    }

    /**
     * Metodo statico per ricavare un handler di email partendo da un {@code Predicate} e da un
     * {@code Consumer}. L'handler applica il {@code Consumer} se si verifica {@code Predicate}.
     * 
     * <p>
     * Un handler è pensato per essere concatenato ad altri handler, al fine di relizzare un unico
     * handler, che implementi una chain of responsability. Dato che un precedente handler potrebbe
     * aver gestito la email, essa è trattata come optional.
     * </p>
     * 
     * @param predicate condizione che si deve verificare affinché l'email venga consumata.
     * @param consumer  operazioni da effettuare per consumare l'email.
     * @return Function<Optional<EmailData>, Optional<EmailData>>
     */
    static Function<Optional<EmailData>, Optional<EmailData>> of(Predicate<EmailData> predicate,
            Consumer<EmailData> consumer) {
        return email -> {
            if (email.isPresent()) {
                if (predicate.test(email.get())) {
                    consumer.accept(email.get());
                    return Optional.empty();
                }
            }
            return email;
        };
    }
}
