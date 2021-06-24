package com.github.mouse0w0.observable.value;

public interface ReadOnlyWrapper<T> extends MutableValue<T> {
    ObservableValue<T> toReadOnly();
}
