package es.pryades.nadarin.ui.common;

import com.vaadin.flow.component.Component;

public interface View {
    Component getHeader();
    Component getContentArea();
    Component getFooter();
}
