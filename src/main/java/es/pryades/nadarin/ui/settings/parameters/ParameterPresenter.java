package es.pryades.nadarin.ui.settings.parameters;

import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.common.CalendarUtils;
import es.pryades.nadarin.common.CrudService;
import es.pryades.nadarin.common.Utils;
import es.pryades.nadarin.dto.Parameter;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.ioc.IOCManager;
import es.pryades.nadarin.services.UserService;
import es.pryades.nadarin.ui.common.CrudPresenter;
import es.pryades.nadarin.ui.common.EmptyQuery;

public class ParameterPresenter extends CrudPresenter<Parameter, ParameterView> {

    public ParameterPresenter(AppContext context, CrudService<Parameter> service, EmptyQuery emptyQuery) {
        super(context, service, emptyQuery);
    }

    @Override
    protected void saveEntity() {
        if (!getState().isNew()) {
            getState().updateEntity(getService().save(getContext(), getService().load(getContext(), getState().getEntity().getId()), getState().getEntity()), false);

            IOCManager._ParametersManager.loadParameters( getContext() );
        }
    }

    @Override
    protected Parameter open(Parameter entity) {
        Parameter parameter = super.open(entity);
        ((ParameterForm)getView().getForm()).setCaptionValue(entity.getDescription());
        getView().getForm().getButtons().setDeleteVisible(false);
        return parameter;
    }

    @Override
    public void delete() {

    }
}
