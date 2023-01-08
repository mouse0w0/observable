package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleIntValue extends ObservableValueBase<Integer> implements WritableIntValue {
    private int value;

    public SimpleIntValue() {
    }

    public SimpleIntValue(int value) {
        this.value = value;
    }

    @Override
    public void set(int value) {
        if (this.value != value) {
            this.value = value;
            notifyChanged();
        }
    }

    @Override
    public void setValue(Integer value) {
        Objects.requireNonNull(value);
        set(value);
    }

    @Override
    public int get() {
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
        return value;
    }

    @Override
    public long getLong() {
        return value;
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
    public Integer getValue() {
        return get();
    }

    @Override
    public String toString() {
        return "SimpleIntValue{" +
                "value=" + value +
                '}';
    }
}
