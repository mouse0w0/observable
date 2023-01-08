package com.github.mouse0w0.observable.value;

public interface ObservableFloatValue extends ObservableNumberValue<Float> {

    float get();

    @Override
    default Float getValue() {
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
        return get();
    }

    @Override
    default double doubleValue() {
        return get();
    }
}
