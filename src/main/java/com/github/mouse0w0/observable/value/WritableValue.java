package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.binding.BidirectionalBinding;

public interface WritableValue<T> extends ObservableValue<T> {

    void setValue(T value);

    default void bindBidirectional(WritableValue<T> other) {
        BidirectionalBinding.bind(this, other);
    }

    default void unbindBidirectional(WritableValue<T> other) {
        BidirectionalBinding.unbind(this, other);
    }
}
