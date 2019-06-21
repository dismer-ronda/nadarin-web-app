package es.pryades.nadarin.ui.common;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;

public class FormButtonsBar extends Div {

    private Button save = new Button();
    private Button cancel = new Button();
    private Button delete = new Button();

    public FormButtonsBar(){
        setClassName("buttons-bar");
        add(save, cancel, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.getStyle().set("margin-right", "auto");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    }

    public void setSaveText(String saveText) {
        save.setText(saveText == null ? "" : saveText);
    }

    public void setCancelText(String cancelText) {
        cancel.setText(cancelText == null ? "" : cancelText);
    }

    public void setDeleteText(String deleteText) {
        delete.setText(deleteText == null ? "" : deleteText);
    }

    public void setSaveDisabled(boolean saveDisabled) {
        save.setEnabled(!saveDisabled);
    }

    public void setCancelDisabled(boolean cancelDisabled) {
        cancel.setEnabled(!cancelDisabled);
    }

    public void setDeleteDisabled(boolean deleteDisabled) {
        delete.setEnabled(!deleteDisabled);
    }

    public static class SaveEvent extends ComponentEvent<FormButtonsBar> {
        public SaveEvent(FormButtonsBar source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return save.addClickListener(e -> listener.onComponentEvent(new SaveEvent(this, true)));
    }

    public static class CancelEvent extends ComponentEvent<FormButtonsBar> {
        public CancelEvent(FormButtonsBar source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    public Registration addCancelListener(ComponentEventListener<CancelEvent> listener) {
        return cancel.addClickListener(e -> listener.onComponentEvent(new CancelEvent(this, true)));
    }

    public static class DeleteEvent extends ComponentEvent<FormButtonsBar> {
        public DeleteEvent(FormButtonsBar source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return delete.addClickListener(e -> listener.onComponentEvent(new DeleteEvent(this, true)));
    }
}
