package com.github.mouse0w0.observable.value;

public interface ObservableLongValue extends ObservableNumberValue<Long> {

    long get();

    @Override
    default Long getValue() {
        return get();
    }

    @Override
    default int intValue() {
        return (int) get();
    }

    @Override
    default long longValue() {
        return get();
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
