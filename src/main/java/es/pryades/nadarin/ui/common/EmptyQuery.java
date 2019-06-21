package es.pryades.nadarin.ui.common;

import es.pryades.nadarin.dto.Query;

@FunctionalInterface
public interface EmptyQuery<Q extends Query> {
    Q get();
}
