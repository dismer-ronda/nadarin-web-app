package es.pryades.nadarin.ui.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ElementFactory;
import es.pryades.nadarin.common.BaseException;
import es.pryades.nadarin.common.Utils;
import es.pryades.nadarin.ioc.IOCManager;
import es.pryades.nadarin.ui.NadarinStyles;
import es.pryades.nadarin.ui.common.HasAppContext;
import es.pryades.nadarin.ui.common.HasNotifications;
import org.apache.commons.lang3.StringUtils;

public class ForgotPasswordDialog extends Dialog implements HasNotifications, HasAppContext {
    private H3 title = new H3();
    private FormLayout form = new FormLayout();
    private Div buttonsBar = new Div();
    private TextField userOrEmail;
    private Button send = new Button();
    private Button cancel = new Button();

    public ForgotPasswordDialog() {
        super();
        getElement().setAttribute("theme", NadarinStyles.THEME_DIALOG_SHORT);
        buidComponents();
    }

    private void buidComponents() {
        FlexLayout content = new FlexLayout();
        content.setClassName("edit-form-content");
        title.setText(getTranslation("login.password.forgot"));
        form.add(title);
        form.getElement().appendChild(ElementFactory.createBr());
        userOrEmail = new TextField(getTranslation("login.user.email"));
        form.add(userOrEmail);

        buttonsBar.setClassName("buttons-bar");
        buttonsBar.add(send, cancel);
        send.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        send.setText(getTranslation("login.send.email"));
        send.addClickListener(event -> onRenew());
        cancel.setText(getTranslation("operation.cancel"));
        cancel.addClickListener(event -> close());

        content.add(form, buttonsBar);

        add(content);
    }

    private void onRenew() {
        String email = userOrEmail.getValue();

        if (StringUtils.isEmpty(email)) {
            showNotification(getTranslation("login.error.notblank"), true);
            return;
        }

        try {
            IOCManager._UsersManager.sendNewPassword(getContext(), email, getTranslation("login.message.subject"), getTranslation("login.renew.message.body"), true);

            showNotification(getTranslation("login.password.sent"));
        } catch (BaseException e) {
            switch (e.getErrorCode()) {
                case BaseException.NULL_RETURN:
                    showNotification(getTranslation("LoginDlg.notfound"), true);
                    break;
                case BaseException.MAIL_ADDRESS_INVALID:
                    showNotification(getTranslation("error.email.invalid"), true);
                    break;
                case BaseException.MAIL_SEND_ERROR:
                    showNotification(getTranslation("error.email.fail"), true);
                    break;
                case BaseException.LOGIN_BLOCKED:
                    showNotification(getTranslation("error.blocked"), true);
                    break;
                default:
                    showNotification(getTranslation("error.unknown"), true);
                    break;
            }
        }
    }


}
