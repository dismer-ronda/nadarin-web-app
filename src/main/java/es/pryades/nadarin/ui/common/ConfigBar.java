package es.pryades.nadarin.ui.common;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import es.pryades.nadarin.dto.Query;

public abstract class ConfigBar<Q extends Query> extends SearchBar<Q>{

    public ConfigBar(){
        super();
        removeClassName("top-bar");
    }

    protected abstract void init();

    @Override
    protected void buildBar() {
        filterActionBar = new FlexLayout();
        filterBar = new FlexLayout();
        add(filterActionBar);
        actionsBar = new FlexLayout();
        addBtn = new Button(getAddText(), VaadinIcon.PLUS.create());
        addBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addBtn.setWidth("100%");

        actionsBar.add(addBtn);
        filterActionBar.add(filterBar, actionsBar);
    }

    @Override
    public void addToFilterArea(Component... components){
        //No es necesario
    }

    @Override
    public void filterVisible(boolean visible){
        //filterBar.setVisible(visible);
       // searchBtn.setVisible(visible);
    }

    public abstract Q getQuery();

    @Override
    protected void buildFilterBarComponents(){
        //No es necesario
    }

    @Override
    protected void buildActionBarComponents(){
        //no es necesario
    }

    @Override
    public void addSearchClickListener(ComponentEventListener<ClickEvent<Button>> listener) {

    }
}
