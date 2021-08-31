package com.clintariac.services.utils;

/**
 * Interfaccia funzionale realizzata per definire delle lambda che non ricevono parametri e non
 * restituiscono nulla.
 */

@FunctionalInterface
public interface Procedure {
    void run();

    /**
     * 
     * @param after
     * @return Procedure
     */
    default Procedure andThen(Procedure after) {
        return () -> {
            this.run();
            after.run();
        };
    }

    /**
     * 
     * @param before
     * @return Procedure
     */
    default Procedure compose(Procedure before) {
        return () -> {
            before.run();
            this.run();
        };
    }

    /**
     * 
     * @return Procedure
     */
    static Procedure doNothing() {
        return () -> {
        };
    }
}
