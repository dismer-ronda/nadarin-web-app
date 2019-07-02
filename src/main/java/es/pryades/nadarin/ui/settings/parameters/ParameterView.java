package es.pryades.nadarin.ui.settings.parameters;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.TextRenderer;
import es.pryades.nadarin.dto.Parameter;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.dto.query.UserQuery;
import es.pryades.nadarin.ioc.IOCManager;
import es.pryades.nadarin.services.ParameterService;
import es.pryades.nadarin.services.UserService;
import es.pryades.nadarin.ui.NadarinStyles;
import es.pryades.nadarin.ui.common.CrudForm;
import es.pryades.nadarin.ui.common.CrudPresenter;
import es.pryades.nadarin.ui.common.CrudView;
import es.pryades.nadarin.ui.common.SearchBar;

import java.io.InputStream;

public class ParameterView extends CrudView<Parameter> {

    private ParameterForm form;
    private ParameterSearchBar searchBar;
    private ParameterPresenter presenter;
    private Binder<Parameter> binder = new Binder<>(Parameter.class);

    public ParameterView(){
        super();
        ParameterService service = (ParameterService) IOCManager.getInstanceOf(ParameterService.class);
        presenter = new ParameterPresenter(getContext(), service, () -> new Parameter());
        presenter.setView(this);
        setupEventListeners();
        presenter.filter(getSearchBar().getQuery());

        form.setBinder(binder);
        editDialog.getElement().setAttribute("theme", NadarinStyles.THEME_DIALOG_SHORT);
    }

    @Override
    public Component getHeader() {
        searchBar =  new ParameterSearchBar();

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
        form = new ParameterForm();
    }

    @Override
    protected SearchBar getSearchBar() {
        return searchBar;
    }

    @Override
    protected CrudPresenter<Parameter, ?> getPresenter() {
        return presenter;
    }

    @Override
    public CrudForm<Parameter> getForm() {
        return form;
    }

    @Override
    public Binder<Parameter> getBinder() {
        return binder;
    }

    @Override
    protected String getCreatedMessage() {
        return "";
    }

    @Override
    protected String getUpdatedMessage() {
        return getTranslation("parametersconfig.operation.updated.notification");
    }

    @Override
    protected String getDeletedMessage() {
        return "";
    }

    @Override
    protected void setupGrid() {
        grid.addColumn(Parameter::getDescription).setHeader(getTranslation("parametersconfig.table.description"));
        grid.addColumn(Parameter::getValue).setHeader(getTranslation("parametersconfig.table.value"));
    }

    @Override
    protected String getDeleteConfirmMessage() {
        return getTranslation("usersconfig.confirm.delete");
    }

    @Override
    public Component getFooter() {
        return null;
    }

    @Override
    protected String getEditTitle(){
        return getTranslation("parametersconfig.modify");
    }
}
