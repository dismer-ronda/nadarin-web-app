package es.pryades.nadarin.ui.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.common.BaseException;
import es.pryades.nadarin.common.Constants;
import es.pryades.nadarin.dto.ProfileRight;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.ioc.IOCManager;
import es.pryades.nadarin.ui.common.HasAppContext;
import es.pryades.nadarin.ui.common.HasNotifications;
import es.pryades.nadarin.ui.profile.ChangePasswordDialog;

import java.time.LocalDate;

@Route(LoginView.ROUTE)
@HtmlImport("styles.html")
public class LoginView extends LoginOverlay implements HasAppContext, HasNotifications {

    public static final String ROUTE = "login";

    public LoginView() {
        setI18n(createLoginI18n());

        addLoginListener(event -> onLogin(event.getUsername(), event.getPassword()));

        addForgotPasswordListener(event -> {
            ForgotPasswordDialog form = new ForgotPasswordDialog();
            form.open();
        });

        setOpened(true);
        UI.getCurrent().getPage().executeJavaScript("document.getElementById(\"vaadinLoginUsername\").focus();");
    }

    private void navigateToMainView(){
        VaadinSession.getCurrent().setAttribute(Constants.USER_LOGGED_IN, true);
        UI.getCurrent().navigate("");
    }

    private LoginI18n createLoginI18n() {
        String year = "" + LocalDate.now().getYear();

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Nadarin");
        //i18n.getHeader().setDescription("Teleradiología en la nube");
        i18n.setAdditionalInformation(getTranslation("app.copyright", year));
        i18n.setForm(new LoginI18n.Form());
        i18n.getForm().setTitle(getTranslation("LoginDlg.title"));
        i18n.getForm().setSubmit(getTranslation("words.login"));
        i18n.getForm().setUsername(getTranslation("words.user"));
        i18n.getForm().setPassword(getTranslation("words.password"));
        i18n.getForm().setForgotPassword(getTranslation("login.password.forgot"));
        i18n.getErrorMessage().setMessage(getTranslation("LoginDlg.loginfail"));
        i18n.getErrorMessage().setTitle(null);

        return i18n;
    }

    private void onLogin(String login, String password) {
        AppContext ctx = (AppContext) VaadinSession.getCurrent().getAttribute(Constants.CONTEXT);
        String subject = getTranslation("login.message.subject");
        String body = getTranslation("login.message.body");

        try {
            IOCManager._UsersManager.validateUser(ctx, login, password, subject, body, true);
            if (ctx.isPasswordExpired()) {
                ctx.getUser().setStatus(User.PASS_EXPIRY);
            }

            if (ctx.getUser().getStatus() != User.PASS_OK) {
                changePassword();
            } else {
                login();
            }
        } catch (BaseException e) {
            switch (e.getErrorCode()) {
                case BaseException.NULL_RETURN:
                case BaseException.LOGIN_FAIL:
                    showNotification(getTranslation("LoginDlg.loginfail"), true);
                    break;

                case BaseException.ZERO_SUBSCRIPTIONS:
                    showNotification(getTranslation("LoginDlg.unsubscribed"), true);
                    break;

                case BaseException.LOGIN_PASSWORD_CHANGED:
                    showNotification(getTranslation("LoginDlg.newpass"), true);
                    break;

                case BaseException.LOGIN_BLOCKED:
                    showNotification(getTranslation("error.blocked"), true);
                    break;

                default:
                    showNotification(getTranslation("error.unknown") + e.getErrorCode(), true);
                    break;
            }
        }
        setError(true);
    }

    private void changePassword() {
        String comments = null;
        AppContext ctx = getContext();

        switch (ctx.getUser().getStatus()) {
            case User.PASS_CHANGED:
                comments = ctx.getString("LoginDlg.password.renew");
                break;

            case User.PASS_EXPIRY:
                comments = ctx.getString("LoginDlg.password.expired");
                break;

            case User.PASS_FORGET:
                comments = ctx.getString("LoginDlg.password.forget");
                break;

            case User.PASS_NEW:
                comments = ctx.getString("LoginDlg.password.new");
                break;
        }

        final ChangePasswordDialog dlg = new ChangePasswordDialog(comments);

        dlg.addDialogCloseActionListener(e -> {
            if (dlg.isChanged())
                navigateToMainView();
        });
        dlg.open();
    }

    private void login() {
        AppContext ctx = getContext();
        User usuario = ctx.getUser();

        try {
            ProfileRight query = new ProfileRight();
            query.setRef_profile(usuario.getRef_profile());

            ctx.setRights(IOCManager._ProfilesRightsManager.getRows(ctx, query));

            if (ctx.hasRight("login")) {
                navigateToMainView();
            } else {
                showNotification(getTranslation("error.login.right"), true);
            }
        } catch (BaseException e) {
            showNotification(getTranslation("error.unknown") + " " + e.getErrorCode(), true);
        }
    }
}
