package es.pryades.nadarin.ui.common;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import es.pryades.nadarin.dto.Query;

import java.util.Objects;

public abstract class SearchBar<Q extends Query> extends FlexLayout {

    Button showBarBtn;
    Button searchBtn;
    Button addBtn;
    FlexLayout filterActionBar;
    FlexLayout filterBar;
    FlexLayout actionsBar;

    private static final String SHOW_BAR = "show-bar";

    public SearchBar(){
        setClassName("top-bar");
        buildBar();
        init();
        buildFilterBarComponents();
        buildActionBarComponents();
    }

    protected abstract void init();

    protected void buildBar() {
        //setAlignItems(Alignment.START);

        showBarBtn = new Button(VaadinIcon.ELLIPSIS_DOTS_V.create());
        showBarBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
        showBarBtn.setClassName("button-show-search-bar");


        filterActionBar = new FlexLayout();
        filterActionBar.setClassName("filter-action-bar");
        add(showBarBtn, filterActionBar);

        filterBar = new FlexLayout();
        showBarBtn.addClickListener( event ->{
            if (filterActionBar.getClassNames().contains(SHOW_BAR)) {
                filterActionBar.removeClassName(SHOW_BAR);
            } else {
                filterActionBar.addClassName(SHOW_BAR);
            }
        });
        filterBar.setClassName("search-filter-bar");
        //filterBar.setAlignItems(Alignment.START);

        searchBtn = new Button(VaadinIcon.SEARCH.create());
        searchBtn.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_PRIMARY);
        searchBtn.setClassName("button-filter");
        addSearchClickListener(e -> {
            if (filterActionBar.getClassNames().contains(SHOW_BAR)) {
                filterActionBar.removeClassName(SHOW_BAR);
        }});

        filterBar.add(searchBtn);

        actionsBar = new FlexLayout();
        actionsBar.setClassName("search-action-bar");

        addBtn = new Button(getAddText(), VaadinIcon.PLUS.create());
        addBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addBtn.setWidth("100%");

        actionsBar.add(addBtn);
        addNewClickListener(e -> {
            if (filterActionBar.getClassNames().contains(SHOW_BAR)) {
                filterActionBar.removeClassName(SHOW_BAR);
            }});
        //actionsBar.setAlignItems(FlexComponent.Alignment.END);

        filterActionBar.add(filterBar, actionsBar);
    }

    protected abstract String getAddText();

    public void addToFilterArea(Component... components){
        Objects.requireNonNull(components, "Componentes no pueden ser null");
        for (Component component : components) {
            Objects.requireNonNull(component,
                    "Componente a agregar no pueden ser null");
            filterBar.add(component);
        }
    }

    public void addToActionArea(Component... components){
        Objects.requireNonNull(components, "Componentes no pueden ser null");
        for (Component component : components) {
            Objects.requireNonNull(component,
                    "Componente a agregar no pueden ser null");
            actionsBar.add(component);
        }
    }

    public void actionsVisible(boolean visible){
        actionsBar.setVisible(visible);
    }

    public void newVisible(boolean visible){
        addBtn.setVisible(visible);
    }

    public void filterVisible(boolean visible){
        //filterBar.setVisible(visible);
        searchBtn.setVisible(visible);
    }

    public abstract Q getQuery();

    protected abstract void buildFilterBarComponents();

    protected abstract void buildActionBarComponents();

    public void addSearchClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        searchBtn.addClickListener(listener);
    }

    public void addNewClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        addBtn.addClickListener(listener);
    }
}
