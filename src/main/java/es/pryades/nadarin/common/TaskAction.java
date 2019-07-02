package es.pryades.nadarin.common;

import es.pryades.nadarin.dto.Task;

public interface TaskAction 
{
	void doTask( AppContext ctx, Task task, boolean forced ) throws Throwable;
	TaskActionDataEditor getTaskDataEditor( );
	boolean isUserEnabledForTask( );
}
