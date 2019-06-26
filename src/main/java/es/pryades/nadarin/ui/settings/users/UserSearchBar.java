package es.pryades.nadarin.ui.settings.users;

import es.pryades.nadarin.dto.query.UserQuery;
import es.pryades.nadarin.ui.common.ConfigBar;

public class UserSearchBar extends ConfigBar<UserQuery> {

    public UserSearchBar(){
        super();
        removeClassName("top-bar");
    }

    @Override
    protected void init() {
        filterVisible(false);
    }

    @Override
    protected String getAddText() {
        return getTranslation("operation.add");
    }

    @Override
    public UserQuery getQuery() {
        return new UserQuery();
    }
}
