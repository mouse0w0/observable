package com.github.mouse0w0.observable.collection;

import java.util.Collections;
import java.util.List;

public interface ObservableList<E> extends List<E> {

    void addListener(ListChangeListener<? super E> listener);

    void removeListener(ListChangeListener<? super E> listener);

    default boolean addAll(E... elements) {
        return Collections.addAll(this, elements);
    }
}
