package es.pryades.nadarin.dal;

import java.util.HashMap;

import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.dto.Parameter;


/**
*
* @author dismer.ronda 
* @since 1.0.0.0
*/
public interface ParametersManager extends BaseManager
{
	public HashMap<Long, Parameter> getParameters( AppContext ctx );
	public void loadParameters( AppContext ctx );
}
