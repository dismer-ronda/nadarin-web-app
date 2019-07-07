package es.pryades.nadarin.ui.common;

import com.vaadin.flow.dom.Element;

public class Tooltip {
    public static void addTo(Element elem, String tooltip){
        Element jtooltip = new Element("j-tooltip");
        Element template = new Element("template");
        template.setProperty("innerHTML", tooltip);
        jtooltip.appendChild(template);
        elem.appendChild(jtooltip);
    }
}
