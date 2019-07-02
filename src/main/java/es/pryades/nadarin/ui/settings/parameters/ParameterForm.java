package es.pryades.nadarin.ui.settings.parameters;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.data.validator.EmailValidator;
import es.pryades.nadarin.common.HasLogger;
import es.pryades.nadarin.dto.Parameter;
import es.pryades.nadarin.dto.Profile;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.ioc.IOCManager;
import es.pryades.nadarin.ui.common.AbstractCrudForm;
import es.pryades.nadarin.ui.common.HasAppContext;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParameterForm extends AbstractCrudForm<Parameter> implements HasAppContext, HasLogger {

    private TextField valueField;

    public ParameterForm(){
        super();
    }

    @Override
    protected void addEditComponents() {
        valueField = new TextField(getTranslation("words.name"));
        getForm().add(valueField);
        valueField.getElement().setAttribute("colspan", "2");
    }

    public void setCaptionValue(String caption){
        valueField.setLabel(caption);
    }

    @Override
    public void setBinder(Binder<Parameter> binder) {
        binder.forField(valueField).asRequired(getTranslation("words.required")).withNullRepresentation("").bind(Parameter::getValue, Parameter::setValue);
    }
}
