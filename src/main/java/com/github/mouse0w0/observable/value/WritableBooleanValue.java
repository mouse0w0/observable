package com.github.mouse0w0.observable.value;

public interface WritableBooleanValue extends ObservableBooleanValue, WritableValue<Boolean> {

    void set(boolean value);

    @Override
    default void setValue(Boolean value) {
        set(value != null ? value : false);
    }
}
