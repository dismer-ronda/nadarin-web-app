package es.pryades.nadarin.common;

import es.pryades.nadarin.dto.Task;

public interface TaskAction 
{
	public void doTask( AppContext ctx, Task task, boolean forced ) throws Throwable;
	//public CommonEditor getTaskDataEditor( AppContext context );
	public boolean isUserEnabledForTask( AppContext context );
}
