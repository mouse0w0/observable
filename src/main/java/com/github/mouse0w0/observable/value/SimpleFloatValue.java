package com.github.mouse0w0.observable.value;

public class SimpleFloatValue extends ObservableValueBase<Float> implements WritableFloatValue {
    private float value;

    public SimpleFloatValue() {
    }

    public SimpleFloatValue(float value) {
        this.value = value;
    }

    @Override
    public float get() {
        valid = true;
        return value;
    }

    @Override
    public void set(float value) {
        if (this.value != value) {
            this.value = value;
            invalidate();
        }
    }

    @Override
    public void setValue(Float value) {
        set(value);
    }

    @Override
    public String toString() {
        return "SimpleFloatValue{" +
                "value=" + value +
                '}';
    }
}
