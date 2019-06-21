package es.pryades.nadarin.services;

import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.dal.BaseManager;
import es.pryades.nadarin.dal.UsersManager;
import es.pryades.nadarin.dto.BaseDto;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.dto.query.UserQuery;
import org.apache.tapestry5.ioc.annotations.Inject;

public class UserServiceImpl implements UserService {
    @Inject
    private UsersManager manager;

    @Override
    public BaseManager getManager() {
        return manager;
    }

    @Override
    public User createNew( ) {
        User user = new User();
        user.setStatus(User.PASS_NEW );
        return user;
    }

    @Override
    public BaseDto emptyQuery() {
        return new UserQuery();
    }

    @Override
    public User getUser(AppContext ctx, long id) {
        User user = new User();
        user.setId(id);
        return (User)manager.getRow(ctx, user);
    }
}
