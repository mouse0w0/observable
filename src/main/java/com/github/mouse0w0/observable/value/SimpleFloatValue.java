package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleFloatValue extends ObservableValueBase<Float> implements MutableFloatValue {
    private float value;

    public SimpleFloatValue() {
    }

    public SimpleFloatValue(float value) {
        this.value = value;
    }

    @Override
    public void set(float value) {
        if (Float.floatToIntBits(this.value) == Float.floatToIntBits(value)) {
            return;
        }
        float oldValue = this.value;
        this.value = value;
        fireValueChangedEvent(oldValue, value);
    }

    @Override
    public void setValue(Float value) {
        Objects.requireNonNull(value);
        set(value);
    }

    @Override
    public float get() {
        return value;
    }

    @Override
    public byte getByte() {
        return (byte) value;
    }

    @Override
    public short getShort() {
        return (short) value;
    }

    @Override
    public int getInt() {
        return (int) value;
    }

    @Override
    public long getLong() {
        return (long) value;
    }

    @Override
    public float getFloat() {
        return value;
    }

    @Override
    public double getDouble() {
        return value;
    }

    @Override
    public Float getValue() {
        return get();
    }

    @Override
    public String toString() {
        return "SimpleFloatValue{" +
                "value=" + value +
                '}';
    }
}
