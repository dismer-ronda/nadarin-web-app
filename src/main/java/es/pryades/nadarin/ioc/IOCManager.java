package es.pryades.nadarin.ioc;

import es.pryades.nadarin.dal.*;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;

import java.io.IOException;
import java.io.Serializable;

/**
*
* @author dismer.ronda 
* @since 1.0.0.0
*/

@SuppressWarnings( {"unchecked","rawtypes"}) 
public class IOCManager  implements Serializable 
{
	private static final long serialVersionUID = 6442290324001703150L;

	static IOCManager instance = null;
	
	private RegistryBuilder builder;
	private Registry registry;

	public static UsersManager _UsersManager;
	public static RightsManager _RightsManager;
	public static ProfilesManager _ProfilesManager;
	public static ProfilesRightsManager _ProfilesRightsManager;
	public static ParametersManager _ParametersManager;
	public static UserDefaultsManager _UserDefaultsManager;
	public static TasksManager _TasksManager;

	public IOCManager() 
	{
		super();
		
		builder = new RegistryBuilder();
		builder.add( StandaloneModule.class );

		registry = builder.build();
		registry.performRegistryStartup();
	}

	/**
	 * Obtiene la instancia del manager de IOC
	 * 
	 * @return <code>IOCManager</code> Devuelve la instancia global del manager de IOC
	 *         
	 */
	public static IOCManager getInstance()
	{
		return instance;
	}
	
	/**
	 * Inicializa el manager de IOC 
	 * 
	 * @return <code>IOCManager</code> Devuelve una instancia del manager global de IOC
	 *         
	 */
	public static void Init() throws IOException
	{
		if ( instance == null )
			instance = new IOCManager();
		
		_UsersManager = (UsersManager)getInstanceOf( UsersManager.class );
		_RightsManager = (RightsManager)getInstanceOf( RightsManager.class );
		_ProfilesManager = (ProfilesManager)getInstanceOf( ProfilesManager.class );
		_ProfilesRightsManager = (ProfilesRightsManager)getInstanceOf( ProfilesRightsManager.class );
		_ParametersManager = (ParametersManager)getInstanceOf( ParametersManager.class );
		_UserDefaultsManager = (UserDefaultsManager)getInstanceOf( UserDefaultsManager.class );
		_TasksManager = (TasksManager)getInstanceOf( TasksManager.class );
	}
	
	/**
	 * Obtiene una instancia de un servicio
	 * 
	 * @param clazz: Interfaz del servicio solicitado.
	 * @return <code>Object</code> Devuelve una instancia de la interfaz del servicio solicitado
	 *         
	 */
	public static Object getInstanceOf( Class clazz )
	{
		return getInstance().registry.getService( clazz );
	}
}
