package es.pryades.nadarin.ui.settings.tasks;

import es.pryades.nadarin.dto.Task;
import es.pryades.nadarin.dto.query.UserQuery;
import es.pryades.nadarin.ui.common.ConfigBar;

public class TaskSearchBar extends ConfigBar<Task> {

    public TaskSearchBar(){
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
    public Task getQuery() {
        return new Task();
    }
}
