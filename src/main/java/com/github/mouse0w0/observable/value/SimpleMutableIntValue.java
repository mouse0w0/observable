package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleMutableIntValue extends ObservableValueBase<Integer> implements MutableIntValue {

    private int value;
    private UnmodifiableIntValue unmodifiableIntValue;

    public SimpleMutableIntValue() {
    }

    public SimpleMutableIntValue(int value) {
        this.value = value;
    }

    @Override
    public void set(int value) {
        if (this.value == value) {
            return;
        }
        int oldValue = this.value;
        this.value = value;
        fireValueChangeEvent(oldValue, value);
    }

    @Override
    public void setValue(Integer value) {
        Objects.requireNonNull(value);
        set(value);
    }

    @Override
    public ObservableIntValue toUnmodifiable() {
        if (unmodifiableIntValue == null)
            unmodifiableIntValue = new UnmodifiableIntValue();
        return unmodifiableIntValue;
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
        return value;
    }

    @Override
    public String toString() {
        return "SimpleMutableIntValue{" +
                "value=" + value +
                '}';
    }

    private class UnmodifiableIntValue implements ObservableIntValue {
        @Override
        public int get() {
            return SimpleMutableIntValue.this.get();
        }

        @Override
        public byte getByte() {
            return SimpleMutableIntValue.this.getByte();
        }

        @Override
        public short getShort() {
            return SimpleMutableIntValue.this.getShort();
        }

        @Override
        public int getInt() {
            return SimpleMutableIntValue.this.getInt();
        }

        @Override
        public long getLong() {
            return SimpleMutableIntValue.this.getLong();
        }

        @Override
        public float getFloat() {
            return SimpleMutableIntValue.this.getFloat();
        }

        @Override
        public double getDouble() {
            return SimpleMutableIntValue.this.getDouble();
        }

        @Override
        public Integer getValue() {
            return SimpleMutableIntValue.this.getValue();
        }

        @Override
        public void addChangeListener(ValueChangeListener<? super Integer> listener) {
            SimpleMutableIntValue.this.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(ValueChangeListener<? super Integer> listener) {
            SimpleMutableIntValue.this.removeChangeListener(listener);
        }

        @Override
        public String toString() {
            return "UnmodifiableIntValue{" +
                    "value=" + value +
                    '}';
        }
    }
}
