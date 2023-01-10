package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class SimpleLongValue implements WritableLongValue, ObservableValue<Long> {
    private boolean valid = true;
    private long value;
    private ListenerHelper<Long> listenerHelper;

    public SimpleLongValue() {
    }

    public SimpleLongValue(long value) {
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
    public void addListener(ChangeListener<? super Long> listener) {
        listenerHelper = ListenerHelper.addListener(this, listenerHelper, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Long> listener) {
        listenerHelper = ListenerHelper.removeListener(listenerHelper, listener);
    }

    protected void invalidate() {
        if (valid) {
            valid = false;
            ListenerHelper.fire(listenerHelper);
        }
    }

    @Override
    public long get() {
        valid = true;
        return value;
    }

    @Override
    public void set(long value) {
        if (this.value != value) {
            this.value = value;
            invalidate();
        }
    }

    @Override
    public void setValue(Long value) {
        set(value);
    }

    @Override
    public String toString() {
        return "SimpleLongValue{" +
                "value=" + value +
                '}';
    }
}
