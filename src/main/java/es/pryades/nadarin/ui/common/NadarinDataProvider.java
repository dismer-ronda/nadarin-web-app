package es.pryades.nadarin.ui.common;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.common.BaseException;
import es.pryades.nadarin.common.CrudService;
import es.pryades.nadarin.common.HasLogger;
import es.pryades.nadarin.dto.BaseDto;
import es.pryades.nadarin.dto.Query;

import java.util.Objects;
import java.util.stream.Stream;

public class NadarinDataProvider<T extends BaseDto, Q extends Query> extends AbstractBackEndDataProvider<T, Q> implements HasLogger {

    private final CrudService<T> service;
    private final AppContext context;
    private EmptyQuery<Q> emptyQuery;
    private Q filter = null;

    public NadarinDataProvider(AppContext context, CrudService<T> service){
        this.context = context;
        this.service = service;
    }

    public void setEmptyQuery(EmptyQuery<Q> emptyQuery){
        this.emptyQuery = emptyQuery;
    }

    public void setFilter(Q filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filter cannot be null");
        } else {
            this.filter = filter;
            this.refreshAll();
        }
    }
    @Override
    protected Stream<T> fetchFromBackEnd(com.vaadin.flow.data.provider.Query<T, Q> query) {

        try {
            return service.rows(context, getFilterQuery(query).getFilter().orElse(emptyQuery.get())).stream();
        } catch (BaseException e) {
            getLogger().error("Error leyendo lista", e);
            e.printStackTrace();
        }
        return Stream.empty();
    }

    @Override
    public Object getId(T item) {
        Objects.requireNonNull(item, "Cannot provide an id for a null item.");
        return item.getId();
    }

    @Override
    protected int sizeInBackEnd(com.vaadin.flow.data.provider.Query<T, Q> query) {
        try {
            return (int)service.count(context, getFilterQuery(query).getFilter().orElse(emptyQuery.get()));
        } catch (BaseException e) {
            getLogger().error("Error contando", e);
            e.printStackTrace();
        }
        return 0;
    }

    private com.vaadin.flow.data.provider.Query<T, Q> getFilterQuery(com.vaadin.flow.data.provider.Query<T, Q> t) {
        settingFilterPageAndPageSize(t.getOffset(), t.getLimit());

        return new com.vaadin.flow.data.provider.Query(t.getOffset(), t.getLimit(), t.getSortOrders(), t.getInMemorySorting(), this.filter);
    }


    private void settingFilterPageAndPageSize(int offset, int limit) {
        if (filter == null){
            filter = emptyQuery.get();
        }

        int lastIndex = offset + limit - 1;
        int maxPageSize = lastIndex + 1;

        for(double pageSize = (double)limit; pageSize <= (double)maxPageSize; ++pageSize) {
            int startPage = (int)((double)offset / pageSize);
            int endPage = (int)((double)lastIndex / pageSize);
            if (startPage == endPage) {
                filter.setPageSize((int)pageSize);
                filter.setPageNumber((long)startPage+1);
                return;
            }
        }
        filter.setPageSize((int)maxPageSize+1);
        filter.setPageNumber((long)1);
    }
}
