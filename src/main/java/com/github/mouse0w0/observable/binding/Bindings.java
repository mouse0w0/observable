package com.github.mouse0w0.observable.binding;

import com.github.mouse0w0.observable.value.MutableValue;

public class Bindings {

    public static <T> void bind(MutableValue<T> mutable1, MutableValue<T> mutable2) {
        BidirectionalBinding.bind(mutable1, mutable2);
    }

    public static <T> void unbind(MutableValue<T> mutable1, MutableValue<T> mutable2) {
        BidirectionalBinding.unbind(mutable1, mutable2);
    }
}
