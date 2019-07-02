package es.pryades.nadarin.ui.settings.tasks;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.TextRenderer;
import es.pryades.nadarin.dto.Task;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.dto.query.UserQuery;
import es.pryades.nadarin.ioc.IOCManager;
import es.pryades.nadarin.services.TaskService;
import es.pryades.nadarin.services.UserService;
import es.pryades.nadarin.ui.common.CrudForm;
import es.pryades.nadarin.ui.common.CrudPresenter;
import es.pryades.nadarin.ui.common.CrudView;
import es.pryades.nadarin.ui.common.SearchBar;

import java.io.InputStream;

public class TaskView extends CrudView<Task> {

    private TaskForm form;
    private TaskSearchBar searchBar;
    private TaskPresenter presenter;
    private Binder<Task> binder = new Binder<>(Task.class);

    public TaskView(){
        super();
        TaskService service = (TaskService) IOCManager.getInstanceOf(TaskService.class);
        presenter = new TaskPresenter(getContext(), service, () -> new Task());
        presenter.setView(this);
        setupEventListeners();
        presenter.filter(getSearchBar().getQuery());

        form.setBinder(binder);
    }

    @Override
    public Component getHeader() {
        searchBar =  new TaskSearchBar();

        /*String filename = Utils.getUUID()+".xls";
        Anchor download = new Anchor(new StreamResource(filename, ()-> createResource()), "");
        download.getElement().setAttribute("download", true);
        Button button = new Button(getTranslation("words.download.xls"), VaadinIcon.DOWNLOAD.create());
        button.setWidth("100%");
        download.add(button);
        searchBar.addToActionArea(download);*/

        return searchBar;
    }

    private InputStream createResource() {
        return null;
    }

    @Override
    protected void buildEditForm() {
        form = new TaskForm();
    }

    @Override
    protected SearchBar getSearchBar() {
        return searchBar;
    }

    @Override
    protected CrudPresenter<Task, ?> getPresenter() {
        return presenter;
    }

    @Override
    public CrudForm<Task> getForm() {
        return form;
    }

    @Override
    public Binder<Task> getBinder() {
        return binder;
    }

    @Override
    protected String getCreatedMessage() {
        return getTranslation("taskconfig.operation.created.notification");
    }

    @Override
    protected String getUpdatedMessage() {
        return getTranslation("taskconfig.operation.updated.notification");
    }

    @Override
    protected String getDeletedMessage() {
        return getTranslation("taskconfig.operation.deleted.notification");
    }

    @Override
    protected void setupGrid() {
        grid.addColumn(new TextRenderer<>(task -> {
            User query = new User();
            query.setId(task.getRef_user());
            User user = (User)IOCManager._UsersManager.getRow(getContext(), query);
            return user.getName();
        })).setHeader(getTranslation("tasksconfig.table.user_name"));
        grid.addColumn(Task::getDescription).setHeader(getTranslation("tasksconfig.table.description"));
        grid.addColumn(Task::getMonth).setHeader(getTranslation("tasksconfig.table.month"));
        grid.addColumn(Task::getDay).setHeader(getTranslation("tasksconfig.table.day"));
        grid.addColumn(Task::getHour).setHeader(getTranslation("tasksconfig.table.hour"));
        grid.addColumn(Task::getMinute).setHeader(getTranslation("tasksconfig.table.minute"));
        grid.addColumn(new TextRenderer<>(task -> getTranslation("task.clazz." + task.getClazz()))).setHeader(getTranslation("tasksconfig.table.clazz"));
    }

    @Override
    protected String getDeleteConfirmMessage() {
        return getTranslation("taskconfig.confirm.delete");
    }

    @Override
    public Component getFooter() {
        return null;
    }

    @Override
    protected String getEditTitle(){
        return getTranslation("taskconfig.modify");
    }

    @Override
    protected String getNewTitle(){
        return getTranslation("taskconfig.new");
    }
}
