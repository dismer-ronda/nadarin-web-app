package es.pryades.nadarin.ui.settings.tasks;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import es.pryades.nadarin.common.*;
import es.pryades.nadarin.dto.Task;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.dto.query.UserQuery;
import es.pryades.nadarin.ioc.IOCManager;
import es.pryades.nadarin.processors.SignalsProcessor;
import es.pryades.nadarin.ui.common.AbstractCrudForm;
import es.pryades.nadarin.ui.common.HasAppContext;
import es.pryades.nadarin.ui.common.HasNotifications;
import es.pryades.nadarin.ui.common.Tooltip;
import lombok.AccessLevel;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TaskForm extends AbstractCrudForm<Task> implements HasAppContext, HasLogger, HasNotifications {

    private ComboBox<Integer> typeComboBox;
    private TextField descriptionField;
    private ComboBox<String> timeZoneComboBox;
    private ComboBox<String> languageComboBox;
    private TextField monthField;
    private TextField dayField;
    private TextField hourField;
    private TextField minuteField;
    private TextField timesField;
    private ComboBox<Long> userComboBox;
    Button executeNow;

    private Map<Long, User> userMap;
    private Div componentTask;
    private TaskActionDataEditor actionEditor;

    @Setter(AccessLevel.PROTECTED)
    private Task task;


    public TaskForm() {
        super();
    }

    @Override
    protected void addEditComponents() {
        typeComboBox = new ComboBox<>(getTranslation("taskconfig.clazz"));
        typeComboBox.setItems(Constants.TASK_CLAZZES);

        typeComboBox.setItemLabelGenerator(value -> getTranslation("task.clazz." + value));
        typeComboBox.addValueChangeListener(event -> onChangeType());

        descriptionField = new TextField(getTranslation("taskconfig.description"));
        timeZoneComboBox = new ComboBox<>(getTranslation("taskconfig.timezone"));
        timeZoneComboBox.setItems(Utils.getTimezones());

        languageComboBox = new ComboBox<>(getTranslation("taskconfig.language"));
        languageComboBox.setItems(Settings.LANGUAGES.split(","));
        languageComboBox.setItemLabelGenerator(value -> getTranslation("language." + value));

        monthField = new TextField(getTranslation("taskconfig.month"));
        monthField.setWidth("100%");
        Icon iconMonth = new Icon(VaadinIcon.QUESTION);
        Tooltip.addTo(iconMonth.getElement(), getTranslation("taskconfig.month.hint"));
        HorizontalLayout divMonth = new HorizontalLayout(monthField, iconMonth);
        divMonth.setAlignItems(Alignment.BASELINE);

        dayField = new TextField(getTranslation("taskconfig.day"));
        dayField.setWidth("100%");
        Icon iconDay = new Icon(VaadinIcon.QUESTION);
        Tooltip.addTo(iconDay.getElement(), getTranslation("taskconfig.day.hint"));
        HorizontalLayout divDay = new HorizontalLayout(dayField, iconDay);
        divDay.setAlignItems(Alignment.BASELINE);

        hourField = new TextField(getTranslation("taskconfig.hour"));
        hourField.setWidth("100%");
        Icon iconHour = new Icon(VaadinIcon.QUESTION);
        Tooltip.addTo(iconHour.getElement(), getTranslation("taskconfig.hour.hint"));
        HorizontalLayout divHour = new HorizontalLayout(hourField, iconHour);
        divHour.setAlignItems(Alignment.BASELINE);

        minuteField = new TextField(getTranslation("taskconfig.minute"));
        minuteField.setWidth("100%");
        Icon iconMinute = new Icon(VaadinIcon.QUESTION);
        Tooltip.addTo(iconMinute.getElement(), getTranslation("taskconfig.minute.hint"));
        HorizontalLayout divMinute = new HorizontalLayout(minuteField, iconMinute);
        divMinute.setAlignItems(Alignment.BASELINE);

        timesField = new TextField(getTranslation("taskconfig.times"));
        timesField.setWidth("100%");
        Icon iconTimes = new Icon(VaadinIcon.QUESTION);
        Tooltip.addTo(iconTimes.getElement(), getTranslation("taskconfig.times.hint"));
        HorizontalLayout divTimes = new HorizontalLayout(timesField, iconTimes);
        divTimes.setAlignItems(Alignment.BASELINE);

        if (getContext().getUser() != null && getContext().getUser().getRef_profile().equals(Constants.ID_PROFILE_DEVELOPER)) {
            userComboBox = new ComboBox<>(getTranslation("taskconfig.user"));
            fillComboUsers();
        }

        getForm().add(typeComboBox, descriptionField, timeZoneComboBox, languageComboBox, divMonth, divDay, divHour, divMinute, divTimes);
        if (userComboBox != null) {
            getForm().add(userComboBox);
        }
        componentTask = new Div();
        componentTask.setWidth("100%");
        getForm().add(componentTask);
        componentTask.getElement().setAttribute("colspan", "2");

        executeNow = new Button("Ejecutar ahora");
        executeNow.addClickListener(event -> executeNow());
        getButtons().add(executeNow);

    }

    private void executeNow() {
        try {
            SignalsProcessor.dispatchSignal(task);

            showNotification(getTranslation("tasksConfig.dispatched"));
        } catch (Throwable e) {
            getLogger().error("Error", e);
        }
    }

    private void onChangeType() {
        if (typeComboBox.getValue() == null) {
            return;
        }

        showTaskEditor();
    }

    private void showTaskEditor() {
        componentTask.removeAll();
        actionEditor = null;

        try {
            TaskAction action = IOCManager._TasksManager.getTaskAction(typeComboBox.getValue());

            if (action != null) {
                actionEditor = action.getTaskDataEditor();

                if (actionEditor != null) {
                    componentTask.add(actionEditor.getComponent(task.getDetails(), false));
                }
            }
        } catch (Throwable e) {
            getLogger().error("Error", e);
        }
    }

    public String getTaskData() throws ValidationException {
        if (actionEditor == null) return null;

        return actionEditor.getTaskData();
    }

    private void fillComboUsers() {
        try {
            UserQuery query = new UserQuery();

            List<User> users = IOCManager._UsersManager.getRows(getContext(), query);
            List<Long> ids = users.stream().map(user -> user.getId()).collect(Collectors.toList());
            userMap = users.stream().collect(Collectors.toMap(User::getId, Function.identity()));
            userComboBox.setItems(ids);
            userComboBox.setItemLabelGenerator(id -> userMap.get(id).getName());
        } catch (Throwable e) {
            getLogger().error("Error", e);
        }
    }

    @Override
    public void setBinder(Binder<Task> binder) {
        binder.forField(typeComboBox).asRequired(getTranslation("words.required")).bind(Task::getClazz, Task::setClazz);
        binder.forField(descriptionField).asRequired(getTranslation("words.required")).withNullRepresentation("").bind(Task::getDescription, Task::setDescription);
        binder.forField(timeZoneComboBox).bind(Task::getTimezone, Task::setTimezone);
        binder.forField(languageComboBox).bind(Task::getLanguage, Task::setLanguage);
        binder.forField(monthField).asRequired(getTranslation("words.required")).withNullRepresentation("").bind(Task::getMonth, Task::setMonth);
        binder.forField(dayField).asRequired(getTranslation("words.required")).withNullRepresentation("").bind(Task::getDay, Task::setDay);
        binder.forField(hourField).asRequired(getTranslation("words.required")).withNullRepresentation("").bind(Task::getHour, Task::setHour);
        binder.forField(minuteField).asRequired(getTranslation("words.required")).withNullRepresentation("").bind(Task::getMinute, Task::setMinute);
        binder.forField(timesField).asRequired(getTranslation("words.required")).withNullRepresentation("").withConverter(new StringToIntegerConverter(getTranslation("error.integer.value"))).bind(Task::getTimes, Task::setTimes);
        if (userComboBox != null) {
            binder.forField(userComboBox).bind(Task::getRef_user, Task::setRef_user);
        }
    }
}
