package es.pryades.nadarin.dal;

import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.dto.UserDefault;



/**
*
* @author dismer.ronda 
* @since 1.0.0.0
*/
public interface UserDefaultsManager extends BaseManager
{
	UserDefault getUserDefault( AppContext context, String key );
	void setUserDefault( AppContext context, UserDefault def, String value );
}
