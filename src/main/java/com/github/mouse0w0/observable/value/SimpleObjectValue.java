package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class SimpleObjectValue<T> implements WritableObjectValue<T>, ObservableValue<T> {
    private boolean valid = true;
    private T value;
    private ListenerHelper<T> listenerHelper;

    public SimpleObjectValue() {
    }

    public SimpleObjectValue(T value) {
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
    public void addListener(ChangeListener<? super T> listener) {
        listenerHelper = ListenerHelper.addListener(this, listenerHelper, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        listenerHelper = ListenerHelper.removeListener(listenerHelper, listener);
    }

    protected void invalidate() {
        if (valid) {
            valid = false;
            ListenerHelper.fire(listenerHelper);
        }
    }

    @Override
    public T get() {
        valid = true;
        return value;
    }

    @Override
    public void set(T value) {
        if (this.value != value) {
            this.value = value;
            invalidate();
        }
    }

    @Override
    public void setValue(T value) {
        set(value);
    }

    @Override
    public String toString() {
        return "SimpleObjectValue{" +
                "value=" + value +
                '}';
    }
}
