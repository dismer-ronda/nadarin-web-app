package es.pryades.nadarin.ui.common;

import com.vaadin.flow.component.HasText;
import com.vaadin.flow.data.binder.Binder;

public interface CrudForm<T> {
    FormButtonsBar getButtons();

    HasText getFormTitle();

    void setBinder(Binder<T> binder);

}
