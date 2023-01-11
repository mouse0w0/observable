package com.github.mouse0w0.observable.value;

public interface WritableIntegerValue extends WritableNumberValue<Integer>, ObservableIntegerValue {

    void set(int value);

    @Override
    default void setValue(Integer value) {
        set(value != null ? value : 0);
    }

    @Override
    default void setValue(Number value) {
        set(value != null ? value.intValue() : 0);
    }
}
