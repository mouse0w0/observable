package com.github.mouse0w0.observable.value;

public interface WritableBooleanValue extends ObservableBooleanValue, WritableValue<Boolean> {

    void set(boolean value);
}
