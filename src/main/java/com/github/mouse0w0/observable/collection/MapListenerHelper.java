package com.github.mouse0w0.observable.collection;

import java.util.Arrays;

abstract class MapListenerHelper<K, V> {
    public static <K, V> MapListenerHelper<K, V> addListener(MapListenerHelper<K, V> helper, MapChangeListener<? super K, ? super V> listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? new SingleListener<>(listener) : helper.addListener(listener);
    }

    public static <K, V> MapListenerHelper<K, V> removeListener(MapListenerHelper<K, V> helper, MapChangeListener<? super K, ? super V> listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? new SingleListener<>(listener) : helper.removeListener(listener);
    }

    public static <K, V> void fire(MapListenerHelper<K, V> helper, MapChangeListener.Change<? extends K, ? extends V> change) {
        if (helper != null) {
            helper.fire(change);
        }
    }

    protected abstract MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> listener);

    protected abstract MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> listener);

    protected abstract void fire(MapChangeListener.Change<? extends K, ? extends V> change);

    private static final class SingleListener<K, V> extends MapListenerHelper<K, V> {

        private final MapChangeListener<? super K, ? super V> listener;

        public SingleListener(MapChangeListener<? super K, ? super V> listener) {
            this.listener = listener;
        }

        @Override
        protected MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> listener) {
            return new ArrayListener<>(this.listener, listener);
        }

        @Override
        protected MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> listener) {
            return this.listener.equals(listener) ? null : this;
        }

        @Override
        protected void fire(MapChangeListener.Change<? extends K, ? extends V> change) {
            listener.onChanged(change);
        }
    }

    private static final class ArrayListener<K, V> extends MapListenerHelper<K, V> {
        private MapChangeListener<? super K, ? super V>[] listeners;
        private int size;
        private boolean locked;

        public ArrayListener(MapChangeListener<? super K, ? super V> listener0, MapChangeListener<? super K, ? super V> listener1) {
            listeners = new MapChangeListener[]{listener0, listener1};
        }

        @Override
        protected MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> listener) {
            final int oldCapacity = listeners.length;
            if (locked) {
                final int newCapacity = (size < oldCapacity) ? oldCapacity : oldCapacity * 3 / 2 + 1;
                listeners = Arrays.copyOf(listeners, newCapacity);
                locked = false; // listeners changed, unlock it.
            } else if (size == oldCapacity) {
                final int newCapacity = oldCapacity * 3 / 2 + 1;
                listeners = Arrays.copyOf(listeners, newCapacity);
            }
            listeners[size++] = listener;
            return this;
        }

        @Override
        protected MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> listener) {
            for (int i = 0; i < size; i++) {
                if (listeners[i].equals(listener)) {
                    if (size == 2) {
                        return new SingleListener<>(listeners[1 - i]);
                    }
                    final int numMoved = size - i - 1;
                    final MapChangeListener<? super K, ? super V>[] oldListeners = listeners;
                    if (locked) {
                        listeners = new MapChangeListener[listeners.length];
                        System.arraycopy(oldListeners, 0, listeners, 0, i);
                    }
                    if (numMoved > 0) {
                        System.arraycopy(oldListeners, i + 1, listeners, i, numMoved);
                    }
                    size--;
                    if (locked) {
                        locked = false; // listeners changed, unlock it.
                    } else {
                        listeners[size] = null; // release object.
                    }
                    return this;
                }
            }
            return this;
        }

        @Override
        protected void fire(MapChangeListener.Change<? extends K, ? extends V> change) {
            final MapChangeListener<? super K, ? super V>[] listeners = this.listeners;
            final int size = this.size;
            try {
                locked = true;
                for (int i = 0; i < size; i++) {
                    try {
                        listeners[i].onChanged(change);
                    } catch (RuntimeException e) {
                        final Thread currentThread = Thread.currentThread();
                        currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, e);
                    }
                }
            } finally {
                locked = false;
            }
        }
    }
}
