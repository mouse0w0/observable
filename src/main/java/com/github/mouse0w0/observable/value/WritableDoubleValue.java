package com.github.mouse0w0.observable.value;

public interface WritableDoubleValue extends WritableNumberValue<Double>, ObservableDoubleValue {

    void set(double value);

    @Override
    default void setValue(Double value) {
        set(value != null ? value : 0);
    }

    @Override
    default void setValue(Number value) {
        set(value != null ? value.doubleValue() : 0);
    }
}
