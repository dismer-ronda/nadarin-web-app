package es.pryades.nadarin.common;

import es.pryades.nadarin.dal.BaseManager;
import es.pryades.nadarin.dto.BaseDto;
import es.pryades.nadarin.dto.Query;

import java.util.List;

public interface CrudService<T extends BaseDto> {
    BaseManager getManager();

    default T save(AppContext ctx, T old, T entity) throws BaseException {
        getManager().setRow(ctx, old, entity);

        return entity;
    }

    default void delete(AppContext ctx, T entity) throws BaseException {
        if (entity == null) {
            return;
        }
        getManager().delRow(ctx, entity);
    }

    default void delete(AppContext ctx, long id)  throws BaseException {
        delete(ctx, load(ctx, id));
    }

    default List<T> rows(AppContext ctx, Query query) throws BaseException {
        return (List<T>) getManager().getRows(ctx, query);
    }

    default long count(AppContext ctx, Query query) throws BaseException {
        return getManager().getNumberOfRows(ctx, query);
    }

    default T load(AppContext ctx, long id) throws BaseException {
        BaseDto base = emptyQuery();
        base.setId(id);
        BaseDto entity = getManager().getRow(ctx, base);
        return (T)entity;
    }

    T createNew();

    BaseDto emptyQuery();
}
