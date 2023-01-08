package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleLongValue extends ObservableValueBase<Long> implements MutableLongValue {
    private long value;

    public SimpleLongValue() {
    }

    public SimpleLongValue(long value) {
        this.value = value;
    }

    @Override
    public void set(long value) {
        if (this.value != value) {
            this.value = value;
            notifyChanged();
        }
    }

    @Override
    public void setValue(Long value) {
        Objects.requireNonNull(value);
        set(value);
    }

    @Override
    public long get() {
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
    public Long getValue() {
        return get();
    }

    @Override
    public String toString() {
        return "SimpleLongValue{" +
                "value=" + value +
                '}';
    }
}
