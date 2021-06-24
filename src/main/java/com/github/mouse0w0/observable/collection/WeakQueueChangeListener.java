package com.github.mouse0w0.observable.collection;

import com.github.mouse0w0.observable.WeakListener;

import java.lang.ref.WeakReference;

public final class WeakQueueChangeListener<E> implements QueueChangeListener<E>, WeakListener {

    private final WeakReference<QueueChangeListener<E>> ref;

    public WeakQueueChangeListener(QueueChangeListener<E> listener) {
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
        QueueChangeListener<E> listener = ref.get();
        if (listener != null) {
            listener.onChanged(change);
        } else {
            change.getQueue().removeListener(this);
        }
    }
}
