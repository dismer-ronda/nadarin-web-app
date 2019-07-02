package es.pryades.nadarin.services;

import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.dal.BaseManager;
import es.pryades.nadarin.dal.ParametersManager;
import es.pryades.nadarin.dal.UsersManager;
import es.pryades.nadarin.dto.BaseDto;
import es.pryades.nadarin.dto.Parameter;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.dto.query.UserQuery;
import org.apache.tapestry5.ioc.annotations.Inject;

public class ParameterServiceImpl implements ParameterService {
    @Inject
    private ParametersManager manager;

    @Override
    public BaseManager getManager() {
        return manager;
    }

    @Override
    public Parameter createNew( ) {
        Parameter parameter = new Parameter();
        return parameter;
    }

    @Override
    public BaseDto emptyQuery() {
        return new Parameter();
    }
}
