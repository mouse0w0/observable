package com.github.mouse0w0.observable;

import java.lang.ref.WeakReference;

public final class WeakInvalidationListener implements InvalidationListener, WeakListener {
    private final WeakReference<InvalidationListener> ref;

    public WeakInvalidationListener(InvalidationListener listener) {
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
    public void invalidated(Observable observable) {
        InvalidationListener listener = ref.get();
        if (listener != null) {
            listener.invalidated(observable);
        } else {
            observable.removeListener(this);
        }
    }
}
