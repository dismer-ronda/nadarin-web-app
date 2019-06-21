package es.pryades.nadarin.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;

//@Route(value=View1.ROUTE, layout = MainLayout.class)
public class View1 extends Composite<Div> implements HasComponents {

    public static final String ROUTE = "view1";

    public View1(){
        add(new Label("Esto es la vista 1"));

        for (VaadinIcon icon: VaadinIcon.values()) {

            Button btn = new Button(icon.create());
            btn.setText(icon.toString());
            add(btn);
        }
    }
}
