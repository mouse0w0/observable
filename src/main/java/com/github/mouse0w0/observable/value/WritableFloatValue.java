package com.github.mouse0w0.observable.value;

public interface WritableFloatValue extends WritableNumberValue<Float>, ObservableFloatValue {

    void set(float value);

    @Override
    default void setValue(Float value) {
        set(value != null ? value : 0);
    }

    @Override
    default void setValue(Number value) {
        set(value != null ? value.floatValue() : 0);
    }
}
