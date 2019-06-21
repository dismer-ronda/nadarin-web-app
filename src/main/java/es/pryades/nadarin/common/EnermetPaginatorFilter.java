package es.pryades.nadarin.common;

import es.pryades.nadarin.dto.Query;

/**
 * 
 * @author Dismer Ronda
 *
 */
public interface EnermetPaginatorFilter extends TablePaginator
{
	public Query getPaginatorQuery();
	public void setPaginatorQuery(Query query);
}
