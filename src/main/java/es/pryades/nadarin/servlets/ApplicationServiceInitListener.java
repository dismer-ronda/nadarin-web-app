package es.pryades.nadarin.servlets;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.UIInitListener;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;
import es.pryades.nadarin.common.Constants;
import es.pryades.nadarin.ui.login.LoginView;

public class ApplicationServiceInitListener implements VaadinServiceInitListener{

  @Override
  public void serviceInit(ServiceInitEvent e) {

    e.getSource()
     .addUIInitListener((UIInitListener) uiInitEvent -> {
       uiInitEvent.getUI().addBeforeEnterListener(new SecurityListener());
     });
  }

  private static class SecurityListener implements BeforeEnterListener{
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
      final UI ui = UI.getCurrent();
      final VaadinSession vaadinSession = ui.getSession();

        if (vaadinSession.getAttribute(Constants.USER_LOGGED_IN) == null) {
            VaadinSession.getCurrent().setAttribute("intendedPath", beforeEnterEvent.getLocation().getPath());
             if (!beforeEnterEvent.getNavigationTarget().equals(LoginView.class))
                beforeEnterEvent.rerouteTo(LoginView.class);
        }
    }
  }
}


/*public class BookstoreInitListener implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent initEvent) {
        final AccessControl accessControl = AccessControlFactory.getInstance()
                .createAccessControl();

        initEvent.getSource().addUIInitListener(uiInitEvent -> {
            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                if (!accessControl.isUserSignedIn() && !LoginScreen.class
                        .equals(enterEvent.getNavigationTarget()))
                    enterEvent.rerouteTo(LoginScreen.class);
            });
        });
    }
}*/