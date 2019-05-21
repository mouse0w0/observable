package com.github.mouse0w0.observable.value;

public interface MutableValue<T> extends ObservableValue<T> {

    void setValue(T value);

    ObservableValue<T> toImmutable();
}
