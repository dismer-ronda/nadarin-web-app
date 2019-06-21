package es.pryades.nadarin.dal.ibatis;

import es.pryades.nadarin.common.BaseException;
import es.pryades.nadarin.dto.Right;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.dto.query.RightQuery;

/**
*
* @author dismer.ronda 
* @since 1.0.0.0
*/

public interface UserMapper extends BaseMapper
{
    public void setPassword( User user );
    public void setRetries( User user );
    public void setStatus( User user );
    
    public Right getRight( RightQuery query ) throws BaseException;
}
