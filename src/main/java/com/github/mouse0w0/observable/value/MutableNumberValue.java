package com.github.mouse0w0.observable.value;

public interface MutableNumberValue<T extends Number> extends ObservableNumberValue<T>, MutableValue<T> {

    ObservableNumberValue<T> toImmutable();
}
