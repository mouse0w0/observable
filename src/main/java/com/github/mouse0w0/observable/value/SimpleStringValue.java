package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class SimpleStringValue implements WritableStringValue, ObservableValue<String> {
    private String value;
    private boolean valid = true;
    private ListenerHelper<String> listenerHelper;

    public SimpleStringValue() {
    }

    public SimpleStringValue(String value) {
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
    public void addListener(ChangeListener<? super String> listener) {
        listenerHelper = ListenerHelper.addListener(this, listenerHelper, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super String> listener) {
        listenerHelper = ListenerHelper.removeListener(listenerHelper, listener);
    }

    protected void invalidate() {
        if (valid) {
            valid = false;
            ListenerHelper.fire(listenerHelper);
        }
    }

    @Override
    public String get() {
        valid = true;
        return value;
    }

    @Override
    public void set(String value) {
        if (this.value != value) {
            this.value = value;
            invalidate();
        }
    }
}
