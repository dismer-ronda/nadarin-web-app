package es.pryades.nadarin.ui.profile;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.dom.ElementFactory;
import es.pryades.nadarin.common.BaseException;
import es.pryades.nadarin.common.Utils;
import es.pryades.nadarin.dto.Parameter;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.dto.UserDefault;
import es.pryades.nadarin.ioc.IOCManager;
import es.pryades.nadarin.ui.NadarinStyles;
import es.pryades.nadarin.ui.common.HasAppContext;
import es.pryades.nadarin.ui.common.HasNotifications;
import lombok.Getter;
import lombok.Setter;

public class ChangePasswordDialog extends Dialog implements HasNotifications, HasAppContext {
    private H3 title = new H3();
    private FormLayout form = new FormLayout();
    private Div buttonsBar = new Div();
    private PasswordField newPassword;
    private PasswordField reapeatPassword;
    private Button changePasswordBtn;

    private UserDefault lastPassword1;
    private UserDefault lastPassword2;

    @Getter
    private boolean changed = false;

    private String comment = null;

    public ChangePasswordDialog() {
        super();
        init();
    }

    public ChangePasswordDialog(String comment) {
        super();
        this.comment = comment;
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        init();
    }

    private void init(){
        getElement().setAttribute("theme", NadarinStyles.THEME_DIALOG_SHORT);
        buidComponents();
        lastPassword1 = IOCManager._UserDefaultsManager.getUserDefault(getContext(), UserDefault.LAST_PASSWORD1);
        lastPassword2 = IOCManager._UserDefaultsManager.getUserDefault(getContext(), UserDefault.LAST_PASSWORD2);

        newPassword.focus();
    }

    private void buidComponents() {
        FlexLayout content = new FlexLayout();
        content.setClassName("edit-form-content");
        title.setText(getTranslation("changepassword.title"));
        form.add(title);
        form.getElement().appendChild(ElementFactory.createBr());
        if (comment != null){
            Div div = new Div();
            div.setText(comment);
            div.setWidth("100%");
            form.add(div);
        }
        User user = getContext().getUser();
        newPassword = new PasswordField(getTranslation("changepassword.new"));
        reapeatPassword = new PasswordField(getTranslation("changepassword.repeat"));
        form.add(newPassword, reapeatPassword);

        buttonsBar.setClassName("buttons-bar");

        changePasswordBtn = new Button(getTranslation("words.change"), event -> onChangePassword());
        changePasswordBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelBtn = new Button(getTranslation("words.cancel"), event -> close());
        buttonsBar.add(changePasswordBtn,cancelBtn);

        content.add(form, buttonsBar);
        add(content);
    }

    private void onChangePassword() {
        String pwd1 = newPassword.getValue();
        String pwd2 = reapeatPassword.getValue();

        if (!pwd1.equals(pwd2)) {
            showNotification(getTranslation("changepassword.error.mismatch"), true);
            return;
        }

        try {
            User usuario = getContext().getUser();

            if (assertNotLogin(pwd1) &&
                    assertCurrentPassword(pwd1) &&
                    assertLastPasswords(lastPassword1, lastPassword2, pwd1) &&
                    assertMinSize(pwd1) &&
                    assertUpperCase(pwd1) &&
                    assertDigit(pwd1) &&
                    assertSymbol(pwd1)) {
                saveLastPasswords(getContext().getUser().getPwd());

                usuario.setPwd(pwd1);
                usuario.setStatus(User.PASS_OK);
                usuario.setRetries(0);

                IOCManager._UsersManager.setPassword(getContext(), usuario, "", "", false);

                changed = true;

                showNotification( getTranslation( "ProfileDlg.password.changed" ) );

                close();
            }
        } catch (BaseException e) {
            showNotification(getTranslation("error.unknown") + e.getErrorCode(), true);
        }
    }

    private boolean assertNotLogin(String newPassword) {
        if (getContext().getIntegerParameter(Parameter.PAR_STRENGTH_LOGIN).equals(0))
            return true;

        boolean valid = !newPassword.toUpperCase().contains(getContext().getUser().getLogin().toUpperCase());

        if (!valid)
            showNotification(getTranslation("changepassword.error.not.login"), true);

        return valid;
    }

    private boolean assertCurrentPassword(String newPassword) {
        boolean valid = !Utils.MD5(newPassword).equals(getContext().getUser().getPwd());

        if (!valid)
            showNotification(getTranslation("changepassword.different"), true);

        return valid;
    }

    private boolean assertLastPasswords(UserDefault lastPassword1, UserDefault lastPassword2, String newPassword) {
        if (getContext().getIntegerParameter(Parameter.PAR_STRENGTH_REUSE).equals(0))
            return true;

        boolean valid = !lastPassword1.getData_value().equalsIgnoreCase(Utils.MD5(newPassword)) &&
                !lastPassword2.getData_value().equalsIgnoreCase(Utils.MD5(newPassword));

        if (!valid)
            showNotification(getTranslation("changepassword.different"), true);

        return valid;
    }

    private boolean assertMinSize(String newPassword) {
        if (getContext().getIntegerParameter(Parameter.PAR_STRENGTH_SIZE).equals(0))
            return true;

        int minSize = getContext().getIntegerParameter(Parameter.PAR_PASSWORD_MIN_SIZE);

        boolean valid = newPassword.length() >= minSize;

        if (!valid)
            showNotification(getTranslation("changepassword.error.size").replaceAll("%size%", Integer.toString(minSize)), true);

        return valid;
    }

    private boolean assertUpperCase(String newPassword) {
        if (getContext().getIntegerParameter(Parameter.PAR_STRENGTH_CAPITAL).equals(0))
            return true;

        for (int i = 0; i < newPassword.length(); i++)
            if (Character.isUpperCase(newPassword.charAt(i)))
                return true;

        showNotification(getTranslation("changepassword.error.uppercase"), true);

        return false;
    }

    private boolean assertDigit(String newPassword) {
        if (getContext().getIntegerParameter(Parameter.PAR_STRENGTH_DIGIT).equals(0))
            return true;

        for (int i = 0; i < newPassword.length(); i++)
            if (Character.isDigit(newPassword.charAt(i)))
                return true;

        showNotification(getTranslation("changepassword.digit"), true);

        return false;
    }

    private boolean assertSymbol(String newPassword) {
        if (getContext().getIntegerParameter(Parameter.PAR_STRENGTH_SYMBOL).equals(0))
            return true;

        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";

        for (int i = 0; i < newPassword.length(); i++)
            if (specialChars.indexOf(newPassword.charAt(i)) != -1)
                return true;

        showNotification(getTranslation("changepassword.error.symbol"), true);

        return false;
    }

    private void saveLastPasswords( String lastPassword )
    {
        String last1 = lastPassword1.getData_value().isEmpty() ? lastPassword : lastPassword1.getData_value();

        IOCManager._UserDefaultsManager.setUserDefault( getContext(), lastPassword2, last1 );
        IOCManager._UserDefaultsManager.setUserDefault( getContext(), lastPassword1, lastPassword );
    }
}
