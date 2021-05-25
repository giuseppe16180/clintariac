package com.clintariac.services.utils;

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
