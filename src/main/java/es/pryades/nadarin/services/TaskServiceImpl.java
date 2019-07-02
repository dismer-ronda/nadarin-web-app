package es.pryades.nadarin.services;

import es.pryades.nadarin.dal.BaseManager;
import es.pryades.nadarin.dal.ParametersManager;
import es.pryades.nadarin.dal.TasksManager;
import es.pryades.nadarin.dto.BaseDto;
import es.pryades.nadarin.dto.Parameter;
import es.pryades.nadarin.dto.Task;
import org.apache.tapestry5.ioc.annotations.Inject;

public class TaskServiceImpl implements TaskService {
    @Inject
    private TasksManager manager;

    @Override
    public BaseManager getManager() {
        return manager;
    }

    @Override
    public Task createNew( ) {
        Task task = new Task();
        task.setTimezone( "UTC" );
        //task.setLanguage( getContext().getLanguage() );
        //task.setRef_user( getContext().getUser().getId() );
        task.setMonth( "1" );
        task.setDay( "1" );
        task.setHour( "1" );
        task.setMinute( "1" );
        task.setTimes( -1 );

        return task;
    }

    @Override
    public BaseDto emptyQuery() {
        return new Task();
    }
}
