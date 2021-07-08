package com.github.mouse0w0.observable.value;

import java.util.Arrays;

public abstract class ListenerHelper<T> {
    public static <T> ListenerHelper<T> addListener(ListenerHelper<T> helper, ChangeListener<? super T> listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? new SingleListener<>(listener) : helper.addListener(listener);
    }

    public static <T> ListenerHelper<T> removeListener(ListenerHelper<T> helper, ChangeListener<? super T> listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? new SingleListener<>(listener) : helper.removeListener(listener);
    }

    public static <T> void fireValueChangeEvent(ListenerHelper<T> helper, ObservableValue<T> observable, T oldValue, T newValue) {
        if (helper != null) {
            helper.fireValueChangeEvent(observable, oldValue, newValue);
        }
    }

    protected abstract ListenerHelper<T> addListener(ChangeListener<? super T> listener);

    protected abstract ListenerHelper<T> removeListener(ChangeListener<? super T> listener);

    protected abstract void fireValueChangeEvent(ObservableValue<T> observable, T oldValue, T newValue);

    private static final class SingleListener<T> extends ListenerHelper<T> {

        private final ChangeListener<? super T> listener;

        public SingleListener(ChangeListener<? super T> listener) {
            this.listener = listener;
        }

        @Override
        protected ListenerHelper<T> addListener(ChangeListener<? super T> listener) {
            return new ArrayListener<>(this.listener, listener);
        }

        @Override
        protected ListenerHelper<T> removeListener(ChangeListener<? super T> listener) {
            return this.listener.equals(listener) ? null : this;
        }

        @Override
        protected void fireValueChangeEvent(ObservableValue<T> observable, T oldValue, T newValue) {
            listener.onChanged(observable, oldValue, newValue);
        }
    }

    private static final class ArrayListener<T> extends ListenerHelper<T> {
        private ChangeListener<? super T>[] listeners;
        private int size;
        private boolean locked;

        public ArrayListener(ChangeListener<? super T> listener0, ChangeListener<? super T> listener1) {
            listeners = new ChangeListener[]{listener0, listener1};
        }

        @Override
        protected ListenerHelper<T> addListener(ChangeListener<? super T> listener) {
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
        protected ListenerHelper<T> removeListener(ChangeListener<? super T> listener) {
            for (int i = 0; i < size; i++) {
                if (listeners[i].equals(listener)) {
                    if (size == 2) {
                        return new SingleListener<>(listeners[1 - i]);
                    }
                    final int numMoved = size - i - 1;
                    final ChangeListener<? super T>[] oldListeners = listeners;
                    if (locked) {
                        listeners = new ChangeListener[listeners.length];
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
        protected void fireValueChangeEvent(ObservableValue<T> observable, T oldValue, T newValue) {
            final ChangeListener<? super T>[] listeners = this.listeners;
            final int size = this.size;
            try {
                locked = true;
                for (int i = 0; i < size; i++) {
                    try {
                        listeners[i].onChanged(observable, oldValue, newValue);
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
