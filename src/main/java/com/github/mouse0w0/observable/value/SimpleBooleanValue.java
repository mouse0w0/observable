package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleBooleanValue extends ObservableValueBase<Boolean> implements MutableBooleanValue {
    private boolean value;

    public SimpleBooleanValue() {
    }

    public SimpleBooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public void setValue(Boolean value) {
        Objects.requireNonNull(value);
        set(value);
    }

    @Override
    public boolean get() {
        return value;
    }

    @Override
    public Boolean getValue() {
        return get();
    }

    @Override
    public void set(boolean value) {
        if (this.value != value) {
            this.value = value;
            notifyChanged();
        }
    }

    @Override
    public String toString() {
        return "SimpleBooleanValue{" +
                "value=" + value +
                '}';
    }
}
