package es.pryades.nadarin.servlets;

import es.pryades.nadarin.common.Utils;
import es.pryades.nadarin.processors.TasksProcessor;
import lombok.extern.log4j.Log4j;

@Log4j
public class BootLoader extends Thread
{
    static BootLoader instance;
    
	public static void bootup()
	{
		instance = new BootLoader();
		
		instance.start();
	}
 
	public BootLoader()
	{
	}
	
	@Override
	public void run()
	{
		try
		{
	    	while ( true )
			{
				try
				{
			    	TasksProcessor.startProcessor();

			    	break;
				}
				catch ( Throwable e1 )
				{
					Utils.logException( e1, log );
				}
				
				log.info( "not ready, waiting to retry ..." );
				
				Utils.Sleep( Utils.ONE_SECOND * 30 );
			}
		}
		catch ( Throwable e )
		{
			Utils.logException( e, log );
		}

		log.info( "completed" );
	}
}
