package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleMutableFloatValue extends ObservableValueBase<Float> implements MutableFloatValue {

    private float value;
    private UnmodifiableFloatValue unmodifiableFloatValue;

    public SimpleMutableFloatValue() {
    }

    public SimpleMutableFloatValue(float value) {
        this.value = value;
    }

    @Override
    public void set(float value) {
        if (Float.compare(this.value, value) == 0) {
            return;
        }
        float oldValue = this.value;
        this.value = value;
        fireValueChangeEvent(oldValue, value);
    }

    @Override
    public void setValue(Float value) {
        Objects.requireNonNull(value);
        set(value);
    }

    @Override
    public ObservableFloatValue toUnmodifiable() {
        if (unmodifiableFloatValue == null)
            unmodifiableFloatValue = new UnmodifiableFloatValue();
        return unmodifiableFloatValue;
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
        return "SimpleMutableFloatValue{" +
                "value=" + value +
                '}';
    }

    private class UnmodifiableFloatValue implements ObservableFloatValue {
        @Override
        public float get() {
            return SimpleMutableFloatValue.this.get();
        }

        @Override
        public byte getByte() {
            return SimpleMutableFloatValue.this.getByte();
        }

        @Override
        public short getShort() {
            return SimpleMutableFloatValue.this.getShort();
        }

        @Override
        public int getInt() {
            return SimpleMutableFloatValue.this.getInt();
        }

        @Override
        public long getLong() {
            return SimpleMutableFloatValue.this.getLong();
        }

        @Override
        public float getFloat() {
            return SimpleMutableFloatValue.this.getFloat();
        }

        @Override
        public double getDouble() {
            return SimpleMutableFloatValue.this.getDouble();
        }

        @Override
        public Float getValue() {
            return SimpleMutableFloatValue.this.getValue();
        }

        @Override
        public void addChangeListener(ValueChangeListener<? super Float> listener) {
            SimpleMutableFloatValue.this.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(ValueChangeListener<? super Float> listener) {
            SimpleMutableFloatValue.this.removeChangeListener(listener);
        }

        @Override
        public String toString() {
            return "UnmodifiableFloatValue{" +
                    "value=" + value +
                    '}';
        }
    }
}
