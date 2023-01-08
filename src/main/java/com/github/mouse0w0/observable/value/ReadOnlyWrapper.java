package com.github.mouse0w0.observable.value;

public interface ReadOnlyWrapper<T> extends WritableValue<T> {
    ObservableValue<T> toReadOnly();
}
