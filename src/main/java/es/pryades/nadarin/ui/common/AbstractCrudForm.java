package es.pryades.nadarin.ui.common;

import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.dom.ElementFactory;

public abstract class AbstractCrudForm<T> extends FlexLayout implements CrudForm<T> {

    private H3 title = new H3();
    private FormLayout form = new FormLayout();
    private FormButtonsBar buttonsBar = new FormButtonsBar();

    public AbstractCrudForm(){
        setClassName("edit-form-content");
        form.add(title);
        form.getElement().appendChild(ElementFactory.createBr());
        add(form, buttonsBar);
        addEditComponents();
    }

    protected abstract void addEditComponents();

    protected FormLayout getForm(){
        return form;
    }

    @Override
    public FormButtonsBar getButtons() {
        return buttonsBar;
    }

    @Override
    public HasText getFormTitle() {
        return title;
    }
}
