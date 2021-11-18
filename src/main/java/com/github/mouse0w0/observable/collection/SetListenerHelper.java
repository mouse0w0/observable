package com.github.mouse0w0.observable.collection;

import java.util.Arrays;

abstract class SetListenerHelper<E> {
    public static <E> SetListenerHelper<E> addListener(SetListenerHelper<E> helper, SetChangeListener<? super E> listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? new SingleListener<>(listener) : helper.addListener(listener);
    }

    public static <E> SetListenerHelper<E> removeListener(SetListenerHelper<E> helper, SetChangeListener<? super E> listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? new SingleListener<>(listener) : helper.removeListener(listener);
    }

    public static <E> void fire(SetListenerHelper<E> helper, SetChangeListener.Change<? extends E> change) {
        if (helper != null) {
            helper.fire(change);
        }
    }

    protected abstract SetListenerHelper<E> addListener(SetChangeListener<? super E> listener);

    protected abstract SetListenerHelper<E> removeListener(SetChangeListener<? super E> listener);

    protected abstract void fire(SetChangeListener.Change<? extends E> change);

    private static final class SingleListener<E> extends SetListenerHelper<E> {

        private final SetChangeListener<? super E> listener;

        public SingleListener(SetChangeListener<? super E> listener) {
            this.listener = listener;
        }

        @Override
        protected SetListenerHelper<E> addListener(SetChangeListener<? super E> listener) {
            return new ArrayListener<>(this.listener, listener);
        }

        @Override
        protected SetListenerHelper<E> removeListener(SetChangeListener<? super E> listener) {
            return this.listener.equals(listener) ? null : this;
        }

        @Override
        protected void fire(SetChangeListener.Change<? extends E> change) {
            listener.onChanged(change);
        }
    }

    private static final class ArrayListener<E> extends SetListenerHelper<E> {
        private SetChangeListener<? super E>[] listeners;
        private int size;
        private boolean locked;

        public ArrayListener(SetChangeListener<? super E> listener0, SetChangeListener<? super E> listener1) {
            listeners = new SetChangeListener[]{listener0, listener1};
        }

        @Override
        protected SetListenerHelper<E> addListener(SetChangeListener<? super E> listener) {
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
        protected SetListenerHelper<E> removeListener(SetChangeListener<? super E> listener) {
            for (int i = 0; i < size; i++) {
                if (listeners[i].equals(listener)) {
                    if (size == 2) {
                        return new SingleListener<>(listeners[1 - i]);
                    }
                    final int numMoved = size - i - 1;
                    final SetChangeListener<? super E>[] oldListeners = listeners;
                    if (locked) {
                        listeners = new SetChangeListener[listeners.length];
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
        protected void fire(SetChangeListener.Change<? extends E> change) {
            final SetChangeListener<? super E>[] listeners = this.listeners;
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
