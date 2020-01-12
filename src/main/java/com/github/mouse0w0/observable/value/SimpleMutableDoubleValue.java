package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleMutableDoubleValue extends ObservableValueBase<Double> implements MutableDoubleValue {

    private double value;
    private UnmodifiableDoubleValue unmodifiableDoubleValue;

    public SimpleMutableDoubleValue() {
    }

    public SimpleMutableDoubleValue(double value) {
        this.value = value;
    }

    @Override
    public void set(double value) {
        if (Double.compare(this.value, value) == 0) {
            return;
        }
        double oldValue = this.value;
        this.value = value;
        fireValueChangeEvent(oldValue, value);
    }

    @Override
    public void setValue(Double value) {
        Objects.requireNonNull(value);
        set(value);
    }

    @Override
    public ObservableDoubleValue toUnmodifiable() {
        if (unmodifiableDoubleValue == null)
            unmodifiableDoubleValue = new UnmodifiableDoubleValue();
        return unmodifiableDoubleValue;
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
        return "SimpleMutableDoubleValue{" +
                "value=" + value +
                '}';
    }

    private class UnmodifiableDoubleValue implements ObservableDoubleValue {
        @Override
        public double get() {
            return SimpleMutableDoubleValue.this.get();
        }

        @Override
        public byte getByte() {
            return SimpleMutableDoubleValue.this.getByte();
        }

        @Override
        public short getShort() {
            return SimpleMutableDoubleValue.this.getShort();
        }

        @Override
        public int getInt() {
            return SimpleMutableDoubleValue.this.getInt();
        }

        @Override
        public long getLong() {
            return SimpleMutableDoubleValue.this.getLong();
        }

        @Override
        public float getFloat() {
            return SimpleMutableDoubleValue.this.getFloat();
        }

        @Override
        public double getDouble() {
            return SimpleMutableDoubleValue.this.getDouble();
        }

        @Override
        public Double getValue() {
            return SimpleMutableDoubleValue.this.getValue();
        }

        @Override
        public void addChangeListener(ValueChangeListener<? super Double> listener) {
            SimpleMutableDoubleValue.this.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(ValueChangeListener<? super Double> listener) {
            SimpleMutableDoubleValue.this.removeChangeListener(listener);
        }

        @Override
        public String toString() {
            return "UnmodifiableDoubleValue{" +
                    "value=" + value +
                    '}';
        }
    }
}

