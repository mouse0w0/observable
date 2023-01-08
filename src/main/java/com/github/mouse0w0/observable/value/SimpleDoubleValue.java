package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleDoubleValue extends ObservableValueBase<Double> implements MutableDoubleValue {
    private double value;

    public SimpleDoubleValue() {
    }

    public SimpleDoubleValue(double value) {
        this.value = value;
    }

    @Override
    public void set(double value) {
        if (this.value != value) {
            this.value = value;
            notifyChanged();
        }
    }

    @Override
    public void setValue(Double value) {
        Objects.requireNonNull(value);
        set(value);
    }

    @Override
    public double get() {
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
        return (float) value;
    }

    @Override
    public double getDouble() {
        return value;
    }

    @Override
    public Double getValue() {
        return get();
    }

    @Override
    public String toString() {
        return "SimpleDoubleValue{" +
                "value=" + value +
                '}';
    }
}
