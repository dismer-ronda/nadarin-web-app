package es.pryades.nadarin.ui.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.common.BaseException;
import es.pryades.nadarin.common.Constants;
import es.pryades.nadarin.ioc.IOCManager;

import java.time.LocalDate;

@Route(LoginView.ROUTE)
@HtmlImport("styles.html")
public class LoginView extends LoginOverlay{

    public static final String ROUTE = "login";

    public LoginView(){
        setI18n(createLoginI18n());
        //setForgotPasswordButtonVisible(true);

        addLoginListener(event ->{
            if (onLogin(event.getUsername(), event.getPassword())){
                VaadinSession.getCurrent().setAttribute(Constants.USER_LOGGED_IN, true);
                UI.getCurrent().navigate("");
            }else{
                setError(true);
            }
        });

        addForgotPasswordListener(event ->{
            ForgotPasswordDialog form = new ForgotPasswordDialog();
            form.open();
        });

        setOpened(true);
        UI.getCurrent().getPage().executeJavaScript("document.getElementById(\"vaadinLoginUsername\").focus();");
    }

    private LoginI18n createLoginI18n() {
        String year = ""+LocalDate.now().getYear();

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

    private boolean onLogin(String login, String password){
        AppContext ctx = (AppContext) VaadinSession.getCurrent().getAttribute(Constants.CONTEXT);
        String subject = getTranslation("login.message.subject");
        String body = getTranslation( "LoginDlg.message.body" );

        try {
            IOCManager._UsersManager.validateUser( ctx, login, password, subject, body, true );

        } catch (BaseException e) {
           // UI.getCurrent().navigate(Constants.LOGIN_ERROR_PAGE);
            return false;
        }

        return true;
    }
}
