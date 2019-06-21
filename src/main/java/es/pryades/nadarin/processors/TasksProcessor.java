package es.pryades.nadarin.processors;

import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.common.BaseException;
import es.pryades.nadarin.common.Utils;
import es.pryades.nadarin.dto.Task;
import es.pryades.nadarin.ioc.IOCManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author dismer.ronda
 * @version 
 * @since Jul, 2010
 */

@SuppressWarnings({"unchecked"})
public class TasksProcessor extends TimerTask
{
    private static final Logger LOG = Logger.getLogger( TasksProcessor.class );
    
    private static TasksProcessor processor = null;

    private Timer timer; 
    
	public static void startProcessor() throws BaseException
	{
		processor = new TasksProcessor();

		processor.init();
	}

	public static void stopProcessor()
	{
		synchronized ( processor )
		{
			processor.stopRequest();
		}		
	}
	
	private TasksProcessor()
	{
		super();
	}

	private void init() throws BaseException
	{
    	timer = new Timer();

    	long wait = Utils.getHowMuchToWaitForFirstSecondOfNextMinute(); 
		long repeat = Utils.ONE_MINUTE; 

		timer.schedule( this, wait, repeat );

		LOG.info( "----- started. Waiting " + wait/1000 + " seconds" );
	}
	
	private void stopRequest()
	{
		synchronized ( this )
		{
			timer.cancel();

			LOG.info( "----- finished" );
		}
	}

	@Override
	public void run()
	{
		try 
		{
			AppContext ctx = new AppContext( "en" );
			IOCManager._ParametersManager.loadParameters( ctx );
			
			List<Task> tasks = IOCManager._TasksManager.getRows( ctx, new Task() );

			for ( Task task : tasks )
				IOCManager._TasksManager.doTask( ctx, task, false );
		} 
		catch ( Throwable e ) 
		{
			Utils.logException( e, LOG );
			
			if ( !(e instanceof BaseException) )
				new BaseException( e, LOG, BaseException.UNKNOWN );
		}
		
		LOG.info( "timer finished" );
	}
}
