package com.github.mouse0w0.observable.value;

public interface ObservableIntValue extends ObservableNumberValue<Integer> {

    int get();

    @Override
    default Integer getValue() {
        return get();
    }

    @Override
    default int intValue() {
        return get();
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
