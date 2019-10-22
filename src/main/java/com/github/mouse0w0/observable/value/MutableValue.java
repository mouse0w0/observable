package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.binding.Bindings;

public interface MutableValue<T> extends ObservableValue<T> {

    void setValue(T value);

    ObservableValue<T> toImmutable();

    default void bindBidirectional(MutableValue<T> other) {
        Bindings.bindBidirectional(this, other);
    }

    default void unbindBidirectional(MutableValue<T> other) {
        Bindings.unbindBidirectional(this, other);
    }
}
