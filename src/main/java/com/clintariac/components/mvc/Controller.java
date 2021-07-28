package com.clintariac.components.mvc;

import java.awt.Component;

public interface Controller {

    Component getView();

    default void reloadView() {

    }

    default void updateView() {
        reloadView();
    }
}
