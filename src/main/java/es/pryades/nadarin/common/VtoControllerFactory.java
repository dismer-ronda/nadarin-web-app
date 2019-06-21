package es.pryades.nadarin.common;

import es.pryades.nadarin.dal.BaseManager;
import es.pryades.nadarin.dto.BaseDto;

import java.util.List;

public interface VtoControllerFactory 
{
	public GenericControlerVto getControlerVto( AppContext ctx );
	public BaseDto getFieldDto();
	public BaseManager getFieldManagerImp();
	public void preProcessRows( List<BaseDto> rows );
	//public void onFieldEvent( Component component, String column );
}
