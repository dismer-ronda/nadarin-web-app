package es.pryades.nadarin.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.VaadinSession;
import es.pryades.nadarin.common.Constants;
import es.pryades.nadarin.ui.login.LoginView;
import es.pryades.nadarin.ui.profile.ProfileDialog;
import es.pryades.nadarin.ui.settings.SettingsView;

@HtmlImport("styles.html")
public class MainLayout extends AbstractAppRouterLayout implements BeforeEnterObserver {
    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu appLayoutMenu) {
        appLayout.setBranding(new Span("Nadarin"));

        /*addMenuItem(appLayoutMenu, new AppLayoutMenuItem(VaadinIcon.CROSSHAIRS.create(),
                getAppLayout().getTranslation("targetsConfig.tabName"),
                TargetsView.ROUTE));*/
        addMenuItem(appLayoutMenu, new AppLayoutMenuItem(VaadinIcon.COG.create(),
                getAppLayout().getTranslation("configuration.title"),
                SettingsView.ROUTE));
        addMenuItem(appLayoutMenu, new AppLayoutMenuItem(VaadinIcon.USER_CARD.create(),
                getAppLayout().getTranslation("profile.title"),
                e -> {
                    ProfileDialog dialog = new ProfileDialog();
                    dialog.open();
                }));
        addMenuItem(appLayoutMenu, new AppLayoutMenuItem(VaadinIcon.POWER_OFF.create(),
                getAppLayout().getTranslation("words.logout"),
                e -> {
                    VaadinSession.getCurrent().setAttribute(Constants.USER_LOGGED_IN, null);
                   // VaadinSession.getCurrent().getSession().invalidate();
                    UI.getCurrent().navigate(LoginView.ROUTE);
                }));
    }

    private void addMenuItem(AppLayoutMenu menu, AppLayoutMenuItem menuItem) {
        menuItem.getElement().setAttribute("theme", "icon-on-top");
        menu.addMenuItem(menuItem);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        final UI ui = UI.getCurrent();
        final VaadinSession vaadinSession = ui.getSession();

        if (vaadinSession.getAttribute(Constants.USER_LOGGED_IN) == null) {
            VaadinSession.getCurrent().setAttribute("intendedPath", event.getLocation().getPath());
            if (!event.getNavigationTarget().equals(LoginView.class))
                event.rerouteTo(LoginView.class);
        }
    }
}
