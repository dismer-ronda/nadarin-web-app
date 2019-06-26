package es.pryades.nadarin.ui.settings;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import es.pryades.nadarin.ui.MainLayout;
import es.pryades.nadarin.ui.settings.users.UserView;

import java.util.HashMap;
import java.util.Map;

@Route(value= SettingsView.ROUTE, layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@HtmlImport("styles.html")
@HtmlImport("icomoon-iconset-svg.html")
public class SettingsView extends Composite<VerticalLayout> {

    public static final String ROUTE = "settings";

    private Tabs tabs;
    private Div tabContent;
    private Map<Tab, Component> tabsToView;


    public SettingsView(){
        getContent().setSizeFull();
        tabsToView = new HashMap<>();
        tabs = new Tabs();
        tabs.setWidth("100%");
        getContent().add(tabs);
        tabContent = new Div();
        tabContent.setSizeFull();
        getContent().add(tabContent);
        addUserTabs();

        tabs.addSelectedChangeListener(event -> {
            tabContent.removeAll();
            tabContent.add(tabsToView.get(tabs.getSelectedTab()));
        });


    }

    private void addUserTabs() {
        Tab tabUser = new Tab(getTranslation("usersconfig.tabname"));
        tabs.add(tabUser);
        UserView view = new UserView();
        tabsToView.put(tabUser, view);

        tabs.setSelectedTab(tabUser);
        tabContent.add(view);
    }
}
