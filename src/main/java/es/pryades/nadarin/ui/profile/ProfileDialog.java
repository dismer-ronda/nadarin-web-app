package es.pryades.nadarin.ui.profile;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ElementFactory;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.ui.NadarinStyles;
import es.pryades.nadarin.ui.common.HasAppContext;
import es.pryades.nadarin.ui.common.HasNotifications;

public class ProfileDialog extends Dialog implements HasNotifications, HasAppContext {
    private H3 title = new H3();
    private FormLayout form = new FormLayout();
    private Div buttonsBar = new Div();
    private TextField userName;
    private TextField email;
    private TextField name;
    private Button changePasswordBtn = new Button();

    public ProfileDialog() {
        super();
        getElement().setAttribute("theme", NadarinStyles.THEME_DIALOG_SHORT);
        buidComponents();
    }

    private void buidComponents() {
        FlexLayout content = new FlexLayout();
        content.setClassName("edit-form-content");
        title.setText(getTranslation("profile.title"));
        form.add(title);
        form.getElement().appendChild(ElementFactory.createBr());
        User user = getContext().getUser();
        userName = new TextField(getTranslation("words.login.noun"));
        userName.setValue(user.getLogin());
        userName.setReadOnly(true);
        email = new TextField(getTranslation("words.email"));
        email.setValue(user.getEmail());
        email.setReadOnly(true);
        name = new TextField(getTranslation("words.name"));
        name.setValue(user.getName());
        name.setReadOnly(true);
        form.add(userName, email, name);

        buttonsBar.setClassName("buttons-bar");
        buttonsBar.add(changePasswordBtn);
        changePasswordBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        changePasswordBtn.setText(getTranslation("profile.change.password"));
        changePasswordBtn.addClickListener(event -> {
            ChangePasswordDialog dialog = new ChangePasswordDialog();
            dialog.open();
        });
        content.add(form, buttonsBar);
        add(content);
    }
}
