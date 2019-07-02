package es.pryades.nadarin.dal;

import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.common.Constants;
import es.pryades.nadarin.common.TaskAction;
import es.pryades.nadarin.common.Utils;
import es.pryades.nadarin.dal.ibatis.TaskMapper;
import es.pryades.nadarin.dto.Parameter;
import es.pryades.nadarin.dto.Task;
import es.pryades.nadarin.tasks.*;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
*
* @author dismer.ronda 
* @since 1.0.0.0
*/
public class TasksManagerImpl extends BaseManagerImpl implements TasksManager
{
	private static final long serialVersionUID = 8401885225433302533L;

	private static final Logger LOG = Logger.getLogger( TasksManagerImpl.class );

	public static BaseManager build()
	{
		return new TasksManagerImpl();
	}

	public TasksManagerImpl()
	{
		super( TaskMapper.class, Task.class, LOG );
	}
	
	public TaskAction getTaskAction( Task task )
	{
		switch ( task.getClazz() )
		{
			case Constants.TASK_CLAZZ_DATABASE_UPDATE:
				return new DatabaseUpdateTaskAction();

			case Constants.TASK_CLAZZ_DATABASE_QUERY:
				return new DatabaseQueryTaskAction();
		}
		
		return null;
	}
	
	public TaskAction getTaskAction( int task )
	{
		switch ( task )
		{
			case Constants.TASK_CLAZZ_DATABASE_UPDATE:
				return new DatabaseUpdateTaskAction();

			case Constants.TASK_CLAZZ_DATABASE_QUERY:
				return new DatabaseQueryTaskAction();
		}
		
		return null;
	}

	private boolean isCurrentTimePlanned( Calendar calendar, String times, int field, int offset )
	{
		if ( "*".equals( times.trim() ) )
			return true;

        int time = calendar.get( field ) + offset;

		String parts[] = times.split( "," );
		
		if ( parts.length > 0 )
		{
			for ( String part : parts )
			{
				if ( Utils.getInt( part.trim(), -1 ) == time )
					return true;
			}
		}

		return false;
	}

	private boolean isCurrentDayPlanned( Calendar calendar, String times )
	{
		String dow[] = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };
		
		if ( "*".equals( times.trim() ) )
			return true;

        int dayOfWeek = calendar.get( Calendar.DAY_OF_WEEK ) - 1;
        int dayOfMonth = calendar.get( Calendar.DAY_OF_MONTH );

		String parts[] = times.split( "," );
		
		if ( parts.length > 0 )
		{
			for ( String part : parts )
			{
				if ( part.trim().equals( dow[dayOfWeek] ) ) 
					return true;

				if ( Utils.getInt( part.trim(), -1 ) == dayOfMonth )
					return true;
			}
		}

		return false;
	}

	public void doTask( AppContext ctx, Task task, boolean forced )
	{
		try
		{
			Calendar calendar = GregorianCalendar.getInstance( TimeZone.getTimeZone( task.getTimezone() ) );
	
			boolean isPlanned = isCurrentTimePlanned( calendar, task.getMinute(), Calendar.MINUTE, 0 ) && 
				isCurrentTimePlanned( calendar, task.getHour(), Calendar.HOUR_OF_DAY, 0 ) && 
				isCurrentDayPlanned( calendar, task.getDay() ) &&
				isCurrentTimePlanned( calendar, task.getMonth(), Calendar.MONTH, 1 );
			
			if ( (isPlanned && task.getTimes() >= 0) || forced )
			{
				ctx.setLanguage( task.getLanguage() );

		    	LOG.info( "executing task " + task );
		    	
		    	TaskAction taskAction = getTaskAction( task );
				
				if ( taskAction != null )
				{
					try 
					{
						taskAction.doTask( ctx, task, forced );
						
						if ( task.getTimes() > 0 )
						{
							if ( task.getTimes().equals( 1 ) )
								delRow( ctx, task );
							else
							{
								Task clone = (Task)Utils.clone( task );
								
								task.setTimes( task.getTimes() - 1 );
								
								setRow( ctx, clone, task );
							}
						}
					} 
					catch ( Throwable e ) 
					{
						Utils.logException( e, LOG );
					}
				}
			}
		}
		catch ( Throwable e )
		{
			Utils.logException( e, LOG );
		}
	}

	@Override
	public long getLogSetting() 
	{
		return Parameter.PAR_LOG_TASKS;
	}
}
