package com.github.mouse0w0.observable.collection;

import com.github.mouse0w0.observable.WeakListener;

import java.lang.ref.WeakReference;

public final class WeakListChangeListener<E> implements ListChangeListener<E>, WeakListener {

    private final WeakReference<ListChangeListener<E>> ref;

    public WeakListChangeListener(ListChangeListener<E> listener) {
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
        ListChangeListener<E> listener = ref.get();
        if (listener != null) {
            listener.onChanged(change);
        } else {
            change.getList().removeChangeListener(this);
        }
    }
}
