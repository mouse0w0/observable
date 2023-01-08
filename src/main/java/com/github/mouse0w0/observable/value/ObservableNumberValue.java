package com.github.mouse0w0.observable.value;

public interface ObservableNumberValue<T extends Number> extends ObservableValue<T> {

    int intValue();

    long longValue();

    float floatValue();

    double doubleValue();
}
