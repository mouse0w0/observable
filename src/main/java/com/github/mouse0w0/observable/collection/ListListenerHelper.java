package com.github.mouse0w0.observable.collection;

import java.util.Arrays;

abstract class ListListenerHelper<E> {
    public static <E> ListListenerHelper<E> addListener(ListListenerHelper<E> helper, ListChangeListener<? super E> listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? new SingleListener<>(listener) : helper.addListener(listener);
    }

    public static <E> ListListenerHelper<E> removeListener(ListListenerHelper<E> helper, ListChangeListener<? super E> listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? new SingleListener<>(listener) : helper.removeListener(listener);
    }

    public static <E> void fire(ListListenerHelper<E> helper, ListChangeListener.Change<? extends E> change) {
        if (helper != null) {
            helper.fire(change);
        }
    }

    protected abstract ListListenerHelper<E> addListener(ListChangeListener<? super E> listener);

    protected abstract ListListenerHelper<E> removeListener(ListChangeListener<? super E> listener);

    protected abstract void fire(ListChangeListener.Change<? extends E> change);

    private static final class SingleListener<E> extends ListListenerHelper<E> {

        private final ListChangeListener<? super E> listener;

        public SingleListener(ListChangeListener<? super E> listener) {
            this.listener = listener;
        }

        @Override
        protected ListListenerHelper<E> addListener(ListChangeListener<? super E> listener) {
            return new ArrayListener<>(this.listener, listener);
        }

        @Override
        protected ListListenerHelper<E> removeListener(ListChangeListener<? super E> listener) {
            return this.listener.equals(listener) ? null : this;
        }

        @Override
        protected void fire(ListChangeListener.Change<? extends E> change) {
            listener.onChanged(change);
        }
    }

    private static final class ArrayListener<E> extends ListListenerHelper<E> {
        private ListChangeListener<? super E>[] listeners;
        private int size;
        private boolean locked;

        public ArrayListener(ListChangeListener<? super E> listener0, ListChangeListener<? super E> listener1) {
            listeners = new ListChangeListener[]{listener0, listener1};
        }

        @Override
        protected ListListenerHelper<E> addListener(ListChangeListener<? super E> listener) {
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
        protected ListListenerHelper<E> removeListener(ListChangeListener<? super E> listener) {
            for (int i = 0; i < size; i++) {
                if (listeners[i].equals(listener)) {
                    if (size == 2) {
                        return new SingleListener<>(listeners[1 - i]);
                    }
                    final int numMoved = size - i - 1;
                    final ListChangeListener<? super E>[] oldListeners = listeners;
                    if (locked) {
                        listeners = new ListChangeListener[listeners.length];
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
        protected void fire(ListChangeListener.Change<? extends E> change) {
            final ListChangeListener<? super E>[] listeners = this.listeners;
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
