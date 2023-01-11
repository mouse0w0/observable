package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class SimpleBooleanValue implements WritableBooleanValue, ObservableValue<Boolean> {
    private boolean value;
    private boolean valid = true;
    private ListenerHelper<Boolean> listenerHelper;

    public SimpleBooleanValue() {
    }

    public SimpleBooleanValue(boolean value) {
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
    public void addListener(ChangeListener<? super Boolean> listener) {
        listenerHelper = ListenerHelper.addListener(this, listenerHelper, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Boolean> listener) {
        listenerHelper = ListenerHelper.removeListener(listenerHelper, listener);
    }

    protected void invalidate() {
        if (valid) {
            valid = false;
            ListenerHelper.fire(listenerHelper);
        }
    }

    @Override
    public boolean get() {
        valid = true;
        return value;
    }

    @Override
    public void set(boolean value) {
        if (this.value != value) {
            this.value = value;
            invalidate();
        }
    }
}
