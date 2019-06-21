package es.pryades.nadarin.ioc;

import es.pryades.nadarin.services.*;
import org.apache.tapestry5.ioc.ServiceBinder;

import es.pryades.nadarin.dal.*;

/**
*
* @author dismer.ronda 
* @since 1.0.0.0
*/

public class StandaloneModule 
{
	/**
	 * Registra los servicios soportados mediante IOC
	 * 
	 * @param binder: Binder de servicios en el que estos se registrarán
	 *         
	 */
	public static void bind( ServiceBinder binder )
	{
		binder.bind( UsersManager.class, UsersManagerImpl.class );
		binder.bind( RightsManager.class, RightsManagerImpl.class);
		binder.bind( ProfilesManager.class, ProfilesManagerImpl.class);
		binder.bind( ProfilesRightsManager.class, ProfilesRightsManagerImpl.class);
		binder.bind( ParametersManager.class, ParametersManagerImpl.class);
		binder.bind( UserDefaultsManager.class, UserDefaultsManagerImpl.class);
		binder.bind( TasksManager.class, TasksManagerImpl.class);

		//Services
		binder.bind( UserService.class, UserServiceImpl.class);
	}
}
