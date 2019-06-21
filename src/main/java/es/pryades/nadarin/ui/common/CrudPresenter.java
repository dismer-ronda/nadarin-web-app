package es.pryades.nadarin.ui.common;

import com.vaadin.flow.data.binder.ValidationException;
import es.pryades.nadarin.common.*;
import es.pryades.nadarin.dto.BaseDto;
import es.pryades.nadarin.dto.Query;
import lombok.AccessLevel;
import lombok.Getter;

public class CrudPresenter<T extends BaseDto, V extends CrudView<T>> implements HasLogger {

    @Getter(AccessLevel.PROTECTED)
    private NadarinDataProvider<T, Query> dataProvider;
    @Getter(AccessLevel.PROTECTED)
    private CrudView<T> view;
    @Getter(AccessLevel.PROTECTED)
    private CrudService<T> service;
    @Getter(AccessLevel.PROTECTED)
    private AppContext context;
    @Getter(AccessLevel.PROTECTED)
    private EntityState<T> state = new EntityState<T>();

    public CrudPresenter(AppContext context, CrudService<T> service, EmptyQuery emptyQuery) {
        this.service = service;
        this.context = context;
        dataProvider = new NadarinDataProvider<>(context, service);
        dataProvider.setEmptyQuery(emptyQuery);
    }

    public void setView(CrudView<T> view) {
        this.view = view;
        view.getGrid().setDataProvider(dataProvider);
        dataProvider.refreshAll();
    }

    public void filter(Query filter) {
        dataProvider.setFilter(filter);
    }

    public void loadEntity(Long id) {
        loadEntity(id, this::open);
    }

    private T open(T entity) {
        view.getBinder().readBean(entity);
        view.getForm().getButtons().setDeleteDisabled(state.isNew());
        view.updateTitle(state.isNew());
        view.openDialog();
        return entity;
    }

    public void close() {
        view.closeDialog();
        //view.clear();
        state.clear();
    }

    protected void updateStateEntity(T entity){
        state.updateEntity(entity, false);
    }

    public boolean loadEntity(Long id, CrudOperationListener<T> onSuccess) {
        return executeOperation(() -> {
            state.updateEntity((T) Utils.clone(service.load(context, id)), false);
            onSuccess.execute(state.getEntity());
        });
    }

    public T createNew() {
        state.updateEntity(service.createNew(), true);
        return open(state.getEntity());
    }



    public void save() {
        if (!writeEntity()) return;
        save(e -> {
            if (state.isNew()) {
                view.showCreatedNotification();
                dataProvider.refreshAll();
            } else {
                view.showUpdatedNotification();
                dataProvider.refreshItem(e);
            }
            close();
        });
    }

    public void save(CrudOperationListener<T> onSuccess) {
        if (executeOperation(() -> saveEntity())) {
            onSuccess.execute(state.getEntity());
        }
    }

    protected void saveEntity() {
        if (state.isNew()) {
            state.updateEntity(service.save(context, null, state.getEntity()), true);
        } else {
            state.updateEntity(service.save(context, service.load(context, state.getEntity().getId()), state.getEntity()), false);
        }
    }

    private boolean writeEntity() {
        try {
            view.write(state.getEntity());
            return true;
        } catch (ValidationException e) {
            view.showError(view.getTranslation("words.required"), false);
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void delete() {
        view.showConfirmDelete(event -> delete(e -> {
            close();
            dataProvider.refreshAll();
            view.showDeletedNotification();
        }));
    }

    public void delete(CrudOperationListener<T> onSuccess) {
        if (executeOperation(() -> service.delete(context, state.getEntity()))) {
            onSuccess.execute(state.getEntity());
        }
    }

    private boolean executeOperation(Runnable operation) {
        try {
            operation.run();
            return true;
        } catch (BaseException e) {
            getLogger().error("Error", e);
            consumeError(e, e.getMessage(), true);
        } /*catch (UserFriendlyDataException e) {
            // Commit failed because of application-level data constraints
            consumeError(e, e.getMessage(), true);
        } catch (DataIntegrityViolationException e) {
            // Commit failed because of validation errors
            consumeError(e, CrudErrorMessage.OPERATION_PREVENTED_BY_REFERENCES, true);
        } catch (OptimisticLockingFailureException e) {
            consumeError(e, CrudErrorMessage.CONCURRENT_UPDATE, true);
        } catch (EntityNotFoundException e) {
            consumeError(e, CrudErrorMessage.ENTITY_NOT_FOUND, false);
        } catch (ConstraintViolationException e) {
            consumeError(e, CrudErrorMessage.REQUIRED_FIELDS_MISSING, false);
        }*/
        return false;
    }

    private void consumeError(Exception e, String message, boolean isPersistent) {
        getLogger().debug(message, e);
        view.showError(message, isPersistent);
    }

    @FunctionalInterface
    public interface CrudOperationListener<T> {
        void execute(T entity);
    }
}
