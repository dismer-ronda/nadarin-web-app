package es.pryades.nadarin.dal;

import org.apache.log4j.Logger;

import es.pryades.nadarin.dal.ibatis.RightMapper;
import es.pryades.nadarin.dto.Right;

/**
*
* @author dismer.ronda 
* @since 1.0.0.0
*/
public class RightsManagerImpl extends BaseManagerImpl implements RightsManager
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2332885640873573798L;
	private static final Logger LOG = Logger.getLogger( RightsManagerImpl.class );

	public static BaseManager build()
	{
		return new RightsManagerImpl();
	}

	public RightsManagerImpl()
	{
		super( RightMapper.class, Right.class, LOG );
	}
}
