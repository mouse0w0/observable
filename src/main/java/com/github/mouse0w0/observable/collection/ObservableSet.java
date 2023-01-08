package com.github.mouse0w0.observable.collection;

import com.github.mouse0w0.observable.Observable;

import java.util.Collections;
import java.util.Set;

public interface ObservableSet<E> extends Set<E>, Observable {

    void addListener(SetChangeListener<? super E> listener);

    void removeListener(SetChangeListener<? super E> listener);

    default boolean addAll(E... elements) {
        return Collections.addAll(this, elements);
    }
}
