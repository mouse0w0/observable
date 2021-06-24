package com.github.mouse0w0.observable.value;

public interface MutableObjectValue<T> extends ObservableObjectValue<T>, MutableValue<T> {

    void set(T value);
}
