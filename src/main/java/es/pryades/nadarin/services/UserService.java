package es.pryades.nadarin.services;

import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.common.CrudService;
import es.pryades.nadarin.dto.User;

public interface UserService extends CrudService<User> {

    User getUser(AppContext ctx, long id);
}
