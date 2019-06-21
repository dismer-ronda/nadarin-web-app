package es.pryades.nadarin.servlets;

import org.apache.log4j.Logger;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import es.pryades.nadarin.resources.LoginResource;

public class ServicesServlet extends Application
{
	private static final Logger LOG = Logger.getLogger( ServicesServlet.class );

	public ServicesServlet()
	{
		super();
	}

	@Override
	public Restlet createInboundRoot()
	{
		Router router = new Router( getContext() );

		//router.attach( "/test", TestResource.class );

		router.attach( "/login", LoginResource.class );

		LOG.info( "started" );

		return router;
	}
}
