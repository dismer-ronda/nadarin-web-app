package es.pryades.nadarin.servlets;

import com.vaadin.flow.server.*;
import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.common.Constants;
import es.pryades.nadarin.ioc.IOCManager;

import javax.servlet.ServletException;

public class NadarinServlet extends VaadinServlet implements SessionInitListener, SessionDestroyListener {

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
        getService().addSessionDestroyListener(this);
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        AppContext context = new AppContext();
        IOCManager._ParametersManager.loadParameters( context );
        event.getSession().setAttribute(Constants.CONTEXT, context);
    }

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        event.getSession().setAttribute(Constants.CONTEXT, null);
    }

}
