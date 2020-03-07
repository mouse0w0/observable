package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class NonNullMutableObjectValue<T> extends SimpleMutableObjectValue<T> {

    private final T nullObject;

    public NonNullMutableObjectValue(T nullObject) {
        this.nullObject = Objects.requireNonNull(nullObject, "Null object cannot be null");
        set(nullObject);
    }

    @Override
    public void set(T value) {
        super.set(value == null ? nullObject : value);
    }
}
