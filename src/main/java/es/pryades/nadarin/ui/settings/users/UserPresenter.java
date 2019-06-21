package es.pryades.nadarin.ui.settings.users;

import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.common.CalendarUtils;
import es.pryades.nadarin.common.CrudService;
import es.pryades.nadarin.common.Utils;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.services.UserService;
import es.pryades.nadarin.ui.common.CrudPresenter;
import es.pryades.nadarin.ui.common.EmptyQuery;

public class UserPresenter extends CrudPresenter<User, UserView> {
    public UserPresenter(AppContext context, CrudService<User> service, EmptyQuery emptyQuery) {
        super(context, service, emptyQuery);
    }

    String getUserName(long id){
        User user = ((UserService)getService()).getUser(getContext(), id);

        return user.getName();
    }

    @Override
    protected void saveEntity() {
        if (getState().isNew()) {

            getState().getEntity().setRetries(0);
            getState().getEntity().setStatus(User.PASS_OK);
            getState().getEntity().setChanged(CalendarUtils.getTodayAsInt());
            getState().getEntity().setPwd(Utils.MD5(getState().getEntity().getPwd()));

            getState().updateEntity(getService().save(getContext(), null, getState().getEntity()), true);
        } else {
            User old = getService().load(getContext(), getState().getEntity().getId());
            if ( !getState().getEntity().getPwd().equals( old.getPwd() ) ){
                getState().getEntity().setRetries(0);
                getState().getEntity().setStatus(User.PASS_OK);
                getState().getEntity().setPwd(Utils.MD5(getState().getEntity().getPwd()));
            }
            getState().updateEntity(getService().save(getContext(), getService().load(getContext(), getState().getEntity().getId()), getState().getEntity()), false);
        }
    }
}
