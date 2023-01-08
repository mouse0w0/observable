package com.github.mouse0w0.observable.value;

public interface WritableObjectValue<T> extends ObservableObjectValue<T>, WritableValue<T> {

    void set(T value);
}
