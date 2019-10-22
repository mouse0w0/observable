package com.github.mouse0w0.observable.value;

public interface MutableObjectValue<T> extends MutableValue<T> {

    void set(T value);

    ObservableObjectValue<T> toImmutable();
}
