package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class SimpleIntegerValue implements WritableIntegerValue, ObservableValue<Integer> {
    private int value;
    private boolean valid = true;
    private ListenerHelper<Integer> listenerHelper;

    public SimpleIntegerValue() {
    }

    public SimpleIntegerValue(int value) {
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
    public void addListener(ChangeListener<? super Integer> listener) {
        listenerHelper = ListenerHelper.addListener(this, listenerHelper, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Integer> listener) {
        listenerHelper = ListenerHelper.removeListener(listenerHelper, listener);
    }

    protected void invalidate() {
        if (valid) {
            valid = false;
            ListenerHelper.fire(listenerHelper);
        }
    }

    @Override
    public int get() {
        valid = true;
        return value;
    }

    @Override
    public void set(int value) {
        if (this.value != value) {
            this.value = value;
            invalidate();
        }
    }

    @Override
    public String toString() {
        return "SimpleIntegerValue{" +
                "value=" + value +
                '}';
    }
}
