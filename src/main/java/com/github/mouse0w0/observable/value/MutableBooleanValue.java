package com.github.mouse0w0.observable.value;

public interface MutableBooleanValue extends ObservableBooleanValue, MutableValue<Boolean> {

    void set(boolean value);

    ObservableBooleanValue toImmutable();
}
