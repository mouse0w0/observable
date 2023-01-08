package com.github.mouse0w0.observable.value;

public class SimpleDoubleValue extends ObservableValueBase<Double> implements WritableDoubleValue {
    private double value;

    public SimpleDoubleValue() {
    }

    public SimpleDoubleValue(double value) {
        this.value = value;
    }

    @Override
    public double get() {
        valid = true;
        return value;
    }

    @Override
    public void set(double value) {
        if (this.value != value) {
            this.value = value;
            invalidate();
        }
    }

    @Override
    public void setValue(Double value) {
        set(value);
    }

    @Override
    public String toString() {
        return "SimpleDoubleValue{" +
                "value=" + value +
                '}';
    }
}
