package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.binding.BidirectionalBinding;

public interface MutableValue<T> extends ObservableValue<T> {

    void setValue(T value);

    default void bindBidirectional(MutableValue<T> other) {
        BidirectionalBinding.bind(this, other);
    }

    default void unbindBidirectional(MutableValue<T> other) {
        BidirectionalBinding.unbind(this, other);
    }
}
