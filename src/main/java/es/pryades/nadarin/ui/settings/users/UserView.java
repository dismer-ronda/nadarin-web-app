package es.pryades.nadarin.ui.settings.users;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.TextRenderer;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.dto.query.UserQuery;
import es.pryades.nadarin.ioc.IOCManager;
import es.pryades.nadarin.services.UserService;
import es.pryades.nadarin.ui.common.CrudForm;
import es.pryades.nadarin.ui.common.CrudPresenter;
import es.pryades.nadarin.ui.common.CrudView;
import es.pryades.nadarin.ui.common.SearchBar;

import java.io.InputStream;

public class UserView extends CrudView<User> {

    private UserForm form;
    private UserSearchBar searchBar;
    private UserPresenter presenter;
    private Binder<User> binder = new Binder<>(User.class);

    public UserView(){
        UserService service = (UserService) IOCManager.getInstanceOf(UserService.class);
        presenter = new UserPresenter(getContext(), service, () -> new UserQuery());
        presenter.setView(this);
        setupEventListeners();
        presenter.filter(getSearchBar().getQuery());

        form.setBinder(binder);
    }

    @Override
    public Component getHeader() {
        searchBar =  new UserSearchBar();

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
        form = new UserForm();
    }

    @Override
    protected SearchBar getSearchBar() {
        return searchBar;
    }

    @Override
    protected CrudPresenter<User, ?> getPresenter() {
        return presenter;
    }

    @Override
    protected CrudForm<User> getForm() {
        return form;
    }

    @Override
    protected Binder<User> getBinder() {
        return binder;
    }

    @Override
    protected String getCreatedMessage() {
        return getTranslation("usersconfig.operation.created.notification");
    }

    @Override
    protected String getUpdatedMessage() {
        return getTranslation("usersconfig.operation.updated.notification");
    }

    @Override
    protected String getDeletedMessage() {
        return getTranslation("usersconfig.operation.deleted.notification");
    }

    @Override
    protected void setupGrid() {
        grid.addColumn(User::getName).setHeader(getTranslation("usersconfig.table.name"));
        grid.addColumn(User::getLogin).setHeader(getTranslation("usersconfig.table.login"));
        grid.addColumn(User::getEmail).setHeader(getTranslation("usersconfig.table.email"));
        grid.addColumn(User::getProfile_name).setHeader(getTranslation("usersconfig.table.profile_name"));
        grid.addColumn(new TextRenderer<>(user -> user.getTester().equals(0) ? getTranslation("words.no"):getTranslation("words.yes")))
                .setHeader(getTranslation("usersconfig.table.tester"));


    }

    @Override
    protected String getDeleteConfirmMessage() {
        return getTranslation("usersconfig.confirm.delete");
    }

    @Override
    public Component getFooter() {
        return null;
    }
}
