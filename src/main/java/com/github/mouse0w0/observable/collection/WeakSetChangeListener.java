package com.github.mouse0w0.observable.collection;

import com.github.mouse0w0.observable.WeakListener;

import java.lang.ref.WeakReference;

public final class WeakSetChangeListener<E> implements SetChangeListener<E>, WeakListener {

    private final WeakReference<SetChangeListener<E>> ref;

    public WeakSetChangeListener(SetChangeListener<E> listener) {
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
    public void onChanged(Change<E> change) {
        SetChangeListener<E> listener = ref.get();
        if (listener != null) {
            listener.onChanged(change);
        } else {
            change.getSet().removeChangeListener(this);
        }
    }
}
