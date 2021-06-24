package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.binding.BidirectionalBinding;
import com.github.mouse0w0.observable.binding.UnidirectionalBinding;

public interface MutableValue<T> extends ObservableValue<T> {

    void setValue(T value);

    default void bind(ObservableValue<T> other) {
        UnidirectionalBinding.bind(this, other);
    }

    default void unbind(ObservableValue<T> other) {
        UnidirectionalBinding.unbind(this, other);
    }

    default void bindBidirectional(MutableValue<T> other) {
        BidirectionalBinding.bind(this, other);
    }

    default void unbindBidirectional(MutableValue<T> other) {
        BidirectionalBinding.unbind(this, other);
    }
}
