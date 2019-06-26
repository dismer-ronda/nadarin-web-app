package es.pryades.nadarin.ui.common;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import es.pryades.nadarin.common.HasLogger;
import es.pryades.nadarin.dto.BaseDto;
import org.claspina.confirmdialog.ButtonOption;
import org.claspina.confirmdialog.ConfirmDialog;

public abstract class CrudView<T extends BaseDto> extends Composite<VerticalLayout> implements View, HasLogger, HasAppContext, HasNotifications {

    protected Grid<T> grid = new Grid<>();

    private Dialog editDialog = new Dialog();

    public CrudView(){
        getContent().setSizeFull();
        buildBody();
        buildEditForm();
        editDialog.add((Component) getForm());
        //editDialog.setCloseOnEsc(false);
        //editDialog.setCloseOnOutsideClick(false);
        editDialog.setHeight("100%");
        // Workaround for https://github.com/vaadin/vaadin-dialog-flow/issues/28
        //dialog.getElement().addAttachListener(event -> UI.getCurrent().getPage().executeJavaScript(
        //        "$0.$.overlay.setAttribute('theme', 'center');", dialog.getElement()));
    }

    protected abstract void buildEditForm();

    private void buildBody(){
        Component header = getHeader();
        Component content = getContentArea();
        Component footer = getFooter();
        if (header != null) getContent().add(header);
        if (content != null) getContent().add(content);
        if (footer != null) getContent().add(footer);
        setupGrid();
    }

    @Override
    public Component getHeader() {
        return getSearchBar();
    }

    protected abstract SearchBar getSearchBar();

    protected abstract CrudPresenter<T, ?> getPresenter();

    protected abstract CrudForm<T> getForm();

    protected abstract Binder<T> getBinder();

    public void clear() {
        getBinder().readBean(null);
    }

    public void write(T entity) throws ValidationException {
        getBinder().writeBean(entity);
    }

    public void showError(String message, boolean isPersistent) {
        showNotification(message, isPersistent);
    }

    public void showCreatedNotification(){
        showNotification(getCreatedMessage(), false);
    }

    protected abstract String getCreatedMessage();

    public void showUpdatedNotification(){
        showNotification(getUpdatedMessage(), false);
    }

    protected abstract String getUpdatedMessage();

    public void showDeletedNotification(){
        showNotification(getDeletedMessage(), false);
    }

    protected abstract String getDeletedMessage();

    public Dialog getDialog() {
        return editDialog;
    }

    public void openDialog() {
        editDialog.open();
    }

    public void closeDialog() {
        editDialog.close();
    }

    public void updateTitle(boolean newEntity) {
        getForm().getFormTitle().setText((newEntity ? getNewTitle() : getEditTitle()));
    }

    protected String getEditTitle(){
        return getTranslation("form.edit");
    }

    protected String getNewTitle(){
        return getTranslation("form.new");
    }

    @Override
    public Component getContentArea() {
        grid = getGrid();
        grid.setSizeFull();
        return grid;
    }

    public Grid<T> getGrid(){
        return grid;
    }

    protected abstract void setupGrid();

    public void setupEventListeners(){
        getSearchBar().addSearchClickListener(event -> getPresenter().filter(getSearchBar().getQuery()));
        getSearchBar().addNewClickListener(event -> getPresenter().createNew());

        getGrid().addSelectionListener(e -> {
            e.getFirstSelectedItem().ifPresent(entity -> {
                getPresenter().loadEntity(entity.getId());
                getGrid().deselectAll();
            });
        });

        getForm().getButtons().addCancelListener(e -> getPresenter().close());
        getForm().getButtons().addSaveListener( e -> getPresenter().save());
        getForm().getButtons().addDeleteListener(e -> getPresenter().delete());
    }

    public void showConfirmDelete(ComponentEventListener<ClickEvent<Button>> listenerYes){
        Button yes = new Button();
        yes.addThemeVariants(ButtonVariant.LUMO_ERROR);
        yes.addClickListener(listenerYes);
        Button no = new Button();
        //close.getElement().setAttribute("theme", "error secundary");

        ConfirmDialog
                .create()
                .withCaption(getTranslation("words.confirm"))
                .withMessage(getDeleteConfirmMessage())
                .withButton(no, ButtonOption.caption(getTranslation("words.no")), ButtonOption.closeOnClick(true))
                .withButton(yes, ButtonOption.focus(), ButtonOption.caption(getTranslation("words.yes")), ButtonOption.closeOnClick(true))
                .open();
    }

    protected abstract String getDeleteConfirmMessage();
}
