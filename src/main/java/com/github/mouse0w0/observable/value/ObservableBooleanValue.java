package com.github.mouse0w0.observable.value;

public interface ObservableBooleanValue extends ObservableValue<Boolean> {

    boolean get();

    @Override
    default Boolean getValue() {
        return get();
    }
}
