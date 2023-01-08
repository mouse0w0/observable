package com.github.mouse0w0.observable.value;

public interface ObservableDoubleValue extends ObservableNumberValue<Double> {

    double get();

    @Override
    default Double getValue() {
        return get();
    }

    @Override
    default int intValue() {
        return (int) get();
    }

    @Override
    default long longValue() {
        return (long) get();
    }

    @Override
    default float floatValue() {
        return (float) get();
    }

    @Override
    default double doubleValue() {
        return get();
    }
}
