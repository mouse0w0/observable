package com.github.mouse0w0.observable.collection;

import com.github.mouse0w0.observable.WeakListener;

import java.lang.ref.WeakReference;

public final class WeakMapChangeListener<K, V> implements MapChangeListener<K, V>, WeakListener {

    private final WeakReference<MapChangeListener<K, V>> ref;

    public WeakMapChangeListener(MapChangeListener<K, V> listener) {
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
    public void onChanged(Change<K, V> change) {
        MapChangeListener<K, V> listener = ref.get();
        if (listener != null) {
            listener.onChanged(change);
        } else {
            change.getMap().removeListener(this);
        }
    }
}
