package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.WeakListener;

import java.lang.ref.WeakReference;

public class WeakValueChangeListener<T> implements ValueChangeListener<T>, WeakListener {

    private final WeakReference<ValueChangeListener<T>> ref;

    public WeakValueChangeListener(ValueChangeListener<T> listener) {
        this.ref = new WeakReference<>(listener);
    }

    @Override
    public boolean wasGarbageCollected() {
        return ref.get() == null;
    }

    @Override
    public void onChanged(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        ValueChangeListener<T> listener = ref.get();
        if (listener != null) {
            listener.onChanged(observable, oldValue, newValue);
        } else {
            observable.removeChangeListener(this);
        }
    }
}
