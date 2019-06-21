package es.pryades.nadarin.servlets;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import es.pryades.nadarin.processors.TasksProcessor;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.velocity.app.Velocity;

import es.pryades.nadarin.common.Settings;
import es.pryades.nadarin.common.Utils;
import es.pryades.nadarin.dal.ibatis.DalManager;
import es.pryades.nadarin.ioc.IOCManager;

@Log4j
public class InitServlet extends HttpServlet
{
	private static final long serialVersionUID = 4644675061264784881L;
	
	public void init()
    {
        try 
		{
    		String logFile = Utils.getEnviroment( "LOGFILE" );
    		if ( logFile != null )
    		{
    			try
    			{
        			Logger rootLogger = Logger.getRootLogger();
        			rootLogger.setLevel( Level.INFO );
        			
					RollingFileAppender appender = new RollingFileAppender( new PatternLayout( "[LINDE %d{dd/MM HHmmss.SSS}] %c{1} %m%n" ), logFile );
					appender.setMaxFileSize( "10MB" );
					appender.setMaxBackupIndex( 7 );
					
    				rootLogger.addAppender( appender );
    			}
    			catch (Throwable e)
    			{
    				log.info( "Failed to add appender !!" );
    			}
    		}
            
			Settings.Init();
	    	IOCManager.Init();
	    	
	    	DalManager.Init( Settings.DB_engine,
	    			Settings.DB_driver, 
	    			Settings.DB_url, 
	    			Settings.DB_user, 
	    			Settings.DB_password );

	    	System.setProperty("java.net.preferIPv4Stack" , "true");
	    	
	    	String phantomjs = Utils.getEnviroment( "INSIGHTS_PHANTOMJS" );
	    	if ( phantomjs != null )
	    		System.setProperty( "phantom.exec", phantomjs );

	    	Properties p = new Properties();
	    	p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
	    	Velocity.init( p );

	    	BootLoader.bootup();
	    	
    	    log.info( "started" );
		} 
		catch ( Throwable e ) 
		{
			Utils.logException( e, log );
		}
	}
	
	@Override
	public void destroy()
	{
		log.info( "destroy servlet called" );
		
    	TasksProcessor.stopProcessor();

    	String prefix = getClass().getSimpleName() +" destroy() ";
	    ServletContext ctx1 = getServletContext();
	    
	    try 
	    {
	        Enumeration<Driver> drivers = DriverManager.getDrivers();
	        
	        while(drivers.hasMoreElements()) 
	        {
	            DriverManager.deregisterDriver(drivers.nextElement());
	        }
	    } 
	    catch(Exception e) 
	    {
	        ctx1.log( prefix + "Exception caught while deregistering JDBC drivers", e );
	    }
	    
	    ctx1.log( prefix + " complete" );

	    super.destroy();
	}
}
