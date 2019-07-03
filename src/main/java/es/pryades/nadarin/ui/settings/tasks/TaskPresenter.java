package es.pryades.nadarin.ui.settings.tasks;

import com.vaadin.flow.data.binder.ValidationException;
import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.common.CrudService;
import es.pryades.nadarin.dto.Task;
import es.pryades.nadarin.ui.common.CrudPresenter;
import es.pryades.nadarin.ui.common.EmptyQuery;

public class TaskPresenter extends CrudPresenter<Task, TaskView> {
    public TaskPresenter(AppContext context, CrudService<Task> service, EmptyQuery emptyQuery) {
        super(context, service, emptyQuery);
    }

    @Override
    protected void saveEntity() {
        if (getState().isNew()) {

            getState().updateEntity(getService().save(getContext(), null, getState().getEntity()), true);
        } else {
            getState().updateEntity(getService().save(getContext(), getService().load(getContext(), getState().getEntity().getId()), getState().getEntity()), false);
        }
    }

    @Override
    public Task createNew() {
        Task task = getService().createNew();
        task.setLanguage( getContext().getLanguage() );
        task.setRef_user( getContext().getUser().getId() );
        getState().updateEntity(task, true);
        return open(getState().getEntity());
    }

    @Override
    protected Task open(Task entity) {
        getView().getBinder().readBean(entity);
        getView().getForm().getButtons().setDeleteVisible(!getState().isNew());
        ((TaskForm)getView().getForm()).executeNow.setVisible(!getState().isNew());
        ((TaskForm)getView().getForm()).setTask(entity);
        getView().updateTitle(getState().isNew());
        getView().openDialog();
        return entity;
    }

    @Override
    protected boolean writeEntity() {
        try {
            getView().write(getState().getEntity());
            getState().getEntity().setDetails(((TaskForm)getView().getForm()).getTaskData());
            return true;
        } catch (ValidationException e) {
            getView().showError(getView().getTranslation("words.required"), false);
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

}
