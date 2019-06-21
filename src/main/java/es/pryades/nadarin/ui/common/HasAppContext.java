package es.pryades.nadarin.ui.common;

import com.vaadin.flow.server.VaadinSession;
import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.common.Constants;

public interface HasAppContext {
    default AppContext getContext() {
        return (AppContext) VaadinSession.getCurrent().getAttribute(Constants.CONTEXT);
    }
}
