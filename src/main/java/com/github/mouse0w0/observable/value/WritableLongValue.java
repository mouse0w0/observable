package com.github.mouse0w0.observable.value;

public interface WritableLongValue extends WritableNumberValue<Long>, ObservableLongValue {

    void set(long value);

    @Override
    default void setValue(Long value) {
        set(value != null ? value : 0);
    }

    @Override
    default void setValue(Number value) {
        set(value != null ? value.longValue() : 0);
    }
}
