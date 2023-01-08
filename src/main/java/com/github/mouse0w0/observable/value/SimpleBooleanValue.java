package com.github.mouse0w0.observable.value;

public class SimpleBooleanValue extends ObservableValueBase<Boolean> implements WritableBooleanValue {
    private boolean value;

    public SimpleBooleanValue() {
    }

    public SimpleBooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public boolean get() {
        valid = true;
        return value;
    }

    @Override
    public void set(boolean value) {
        if (this.value != value) {
            this.value = value;
            invalidate();
        }
    }

    @Override
    public void setValue(Boolean value) {
        set(value);
    }

    @Override
    public String toString() {
        return "SimpleBooleanValue{" +
                "value=" + value +
                '}';
    }
}
