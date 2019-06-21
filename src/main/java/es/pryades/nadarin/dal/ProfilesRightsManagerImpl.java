package es.pryades.nadarin.dal;

import org.apache.log4j.Logger;

import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.dal.ibatis.ProfileRightMapper;
import es.pryades.nadarin.dto.Parameter;
import es.pryades.nadarin.dto.ProfileRight;

/**
*
* @author dismer.ronda 
* @since 1.0.0.0
*/
public class ProfilesRightsManagerImpl extends BaseManagerImpl implements ProfilesRightsManager
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4407417331885108347L;
	private static final Logger LOG = Logger.getLogger( ProfilesRightsManagerImpl.class );

	public static BaseManager build()
	{
		return new ProfilesRightsManagerImpl();
	}

	public ProfilesRightsManagerImpl()
	{
		super( ProfileRightMapper.class, ProfileRight.class, LOG );
	}

	@Override
	public boolean hasUniqueId( AppContext ctx ) 
	{
		return false;
	}
	
	@Override
	public long getLogSetting() 
	{
		return Parameter.PAR_LOG_PROFILES_RIGHTS;
	}
}
