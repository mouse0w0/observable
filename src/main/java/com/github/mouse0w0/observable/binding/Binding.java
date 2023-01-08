package com.github.mouse0w0.observable.binding;

import com.github.mouse0w0.observable.collection.ObservableList;
import com.github.mouse0w0.observable.value.ObservableValue;

public interface Binding<T> extends ObservableValue<T> {
    boolean isValid();

    void invalidate();

    ObservableList<?> getDependencies();

    void dispose();
}
