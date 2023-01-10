package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class SimpleDoubleValue implements WritableDoubleValue, ObservableValue<Double> {
    private boolean valid = true;
    private double value;
    private ListenerHelper<Double> listenerHelper;

    public SimpleDoubleValue() {
    }

    public SimpleDoubleValue(double value) {
        this.value = value;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        listenerHelper = ListenerHelper.addListener(this, listenerHelper, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listenerHelper = ListenerHelper.removeListener(listenerHelper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super Double> listener) {
        listenerHelper = ListenerHelper.addListener(this, listenerHelper, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Double> listener) {
        listenerHelper = ListenerHelper.removeListener(listenerHelper, listener);
    }

    protected void invalidate() {
        if (valid) {
            valid = false;
            ListenerHelper.fire(listenerHelper);
        }
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
