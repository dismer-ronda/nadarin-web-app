package es.pryades.nadarin.ui.settings.users;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.TextRenderer;
import es.pryades.nadarin.common.HasLogger;
import es.pryades.nadarin.dto.Profile;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.ioc.IOCManager;
import es.pryades.nadarin.ui.common.AbstractCrudForm;
import es.pryades.nadarin.ui.common.HasAppContext;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserForm extends AbstractCrudForm<User> implements HasAppContext, HasLogger {

    private TextField nameField;
    private TextField loginField;
    private PasswordField passwordField;
    private EmailField emailField;
    private ComboBox<Long> profilesComboBox;
    private RadioButtonGroup<Integer> tester;
    private ComboBox<Integer> passwordStatus;
    private Map<Long, Profile> profileMap;

    public UserForm(){
        super();
    }

    @Override
    protected void addEditComponents() {
        nameField = new TextField(getTranslation("words.name"));
        loginField = new TextField(getTranslation("words.login.noun"));
        passwordField = new PasswordField(getTranslation("words.password"));
        emailField = new EmailField(getTranslation("words.email"));
        profilesComboBox = new ComboBox<>(getTranslation("words.profile"));
        fillComboProfiles();

        tester = new RadioButtonGroup<>();
        tester.setLabel(getTranslation("words.tester"));
        tester.setItems(0, 1);
        tester.setRenderer(new TextRenderer<>(value -> value == 0 ? getTranslation("words.no"):getTranslation("words.yes")));

        passwordStatus = new ComboBox<>(getTranslation("usersconfig.password.status"));
        passwordStatus.setItems(0, 1, 2, 3, 4, 5);
        passwordStatus.setItemLabelGenerator(value -> getTranslation("password.status." + value));

        getForm().add(nameField, loginField, passwordField, emailField, profilesComboBox, tester, passwordStatus);
        nameField.getElement().setAttribute("colspan", "2");
    }

    @Override
    public void setBinder(Binder<User> binder) {
        binder.forField(nameField).asRequired(getTranslation("words.required")).withNullRepresentation("").bind(User::getName, User::setName);
        binder.forField(loginField).asRequired(getTranslation("words.required")).withNullRepresentation("").bind(User::getLogin, User::setLogin);
        binder.forField(passwordField).asRequired(getTranslation("words.required")).withNullRepresentation("").bind(User::getPwd, User::setPwd);
        binder.forField(emailField).asRequired(getTranslation("words.required")).withNullRepresentation("").bind(User::getEmail, User::setEmail);
        binder.forField(profilesComboBox).asRequired(getTranslation("words.required")).bind(User::getRef_profile, User::setRef_profile);
        binder.forField(tester).asRequired(getTranslation("words.required")).bind(User::getTester, User::setTester);
        binder.forField(passwordStatus).asRequired(getTranslation("words.required")).bind(User::getStatus, User::setStatus);
    }

    private void fillComboProfiles() {
        try {
            Profile query = new Profile();

            List<Profile> profiles = IOCManager._ProfilesManager.getRows(getContext(), query);
            List<Long> ids = profiles.stream().map(profile -> profile.getId()).collect(Collectors.toList());
            profileMap = profiles.stream().collect(Collectors.toMap(Profile::getId, Function.identity()));

            profilesComboBox.setItems(ids);
            profilesComboBox.setItemLabelGenerator(id -> profileMap.get(id).getDescription());
        } catch (Throwable e) {
            getLogger().error("Error", e);
        }
    }
}
