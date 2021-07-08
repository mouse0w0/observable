package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.WeakListener;

import java.lang.ref.WeakReference;

public final class WeakChangeListener<T> implements ChangeListener<T>, WeakListener {

    private final WeakReference<ChangeListener<T>> ref;

    public WeakChangeListener(ChangeListener<T> listener) {
        if (listener == null) {
            throw new NullPointerException("listener");
        }
        this.ref = new WeakReference<>(listener);
    }

    @Override
    public boolean wasGarbageCollected() {
        return ref.get() == null;
    }

    @Override
    public void onChanged(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        ChangeListener<T> listener = ref.get();
        if (listener != null) {
            listener.onChanged(observable, oldValue, newValue);
        } else {
            observable.removeListener(this);
        }
    }
}
