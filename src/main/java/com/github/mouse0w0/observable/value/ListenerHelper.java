package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

import java.util.Arrays;

abstract class ListenerHelper<T> {
    public static <T> ListenerHelper<T> addListener(ObservableValue<T> observable, ListenerHelper<T> helper, InvalidationListener listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? new SingleInvalidation<>(observable, listener) : helper.addListener(listener);
    }

    public static <T> ListenerHelper<T> removeListener(ListenerHelper<T> helper, InvalidationListener listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? null : helper.removeListener(listener);
    }

    public static <T> ListenerHelper<T> addListener(ObservableValue<T> observable, ListenerHelper<T> helper, ChangeListener<? super T> listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? new SingleChange<>(observable, listener) : helper.addListener(listener);
    }

    public static <T> ListenerHelper<T> removeListener(ListenerHelper<T> helper, ChangeListener<? super T> listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? null : helper.removeListener(listener);
    }

    public static <T> void fire(ListenerHelper<T> helper) {
        if (helper != null) {
            helper.fire();
        }
    }

    protected final ObservableValue<T> observable;

    public ListenerHelper(ObservableValue<T> observable) {
        this.observable = observable;
    }

    protected abstract ListenerHelper<T> addListener(InvalidationListener listener);

    protected abstract ListenerHelper<T> removeListener(InvalidationListener listener);

    protected abstract ListenerHelper<T> addListener(ChangeListener<? super T> listener);

    protected abstract ListenerHelper<T> removeListener(ChangeListener<? super T> listener);

    protected abstract void fire();

    private static final class SingleInvalidation<T> extends ListenerHelper<T> {
        private final InvalidationListener listener;

        public SingleInvalidation(ObservableValue<T> observable, InvalidationListener listener) {
            super(observable);
            this.listener = listener;
        }

        @Override
        protected ListenerHelper<T> addListener(InvalidationListener listener) {
            return new Generic<>(observable, this.listener, listener);
        }

        @Override
        protected ListenerHelper<T> removeListener(InvalidationListener listener) {
            return this.listener.equals(listener) ? null : this;
        }

        @Override
        protected ListenerHelper<T> addListener(ChangeListener<? super T> listener) {
            return new Generic<>(observable, this.listener, listener);
        }

        @Override
        protected ListenerHelper<T> removeListener(ChangeListener<? super T> listener) {
            return this;
        }

        @Override
        protected void fire() {
            listener.invalidated(observable);
        }
    }

    private static final class SingleChange<T> extends ListenerHelper<T> {
        private final ChangeListener<? super T> listener;
        private T currentValue;

        public SingleChange(ObservableValue<T> observable, ChangeListener<? super T> listener) {
            super(observable);
            this.listener = listener;
            this.currentValue = observable.getValue();
        }

        @Override
        protected ListenerHelper<T> addListener(InvalidationListener listener) {
            return new Generic<>(observable, listener, this.listener);
        }

        @Override
        protected ListenerHelper<T> removeListener(InvalidationListener listener) {
            return this;
        }

        @Override
        protected ListenerHelper<T> addListener(ChangeListener<? super T> listener) {
            return new Generic<>(observable, this.listener, listener);
        }

        @Override
        protected ListenerHelper<T> removeListener(ChangeListener<? super T> listener) {
            return this.listener.equals(listener) ? null : this;
        }

        @Override
        protected void fire() {
            final T oldValue = currentValue;
            currentValue = observable.getValue();
            if (currentValue == null ? oldValue != null : !currentValue.equals(oldValue)) {
                try {
                    listener.onChanged(observable, oldValue, currentValue);
                } catch (RuntimeException e) {
                    final Thread currentThread = Thread.currentThread();
                    currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, e);
                }
            }
        }
    }

    private static final class Generic<T> extends ListenerHelper<T> {
        private InvalidationListener[] invalidationListeners;
        private ChangeListener<? super T>[] changeListeners;
        private int invalidationSize;
        private int changeSize;
        private boolean locked;
        private T currentValue;

        public Generic(ObservableValue<T> observable, InvalidationListener invalidationListener, ChangeListener<? super T> changeListener) {
            super(observable);
            invalidationListeners = new InvalidationListener[]{invalidationListener};
            changeListeners = new ChangeListener[]{changeListener};
            invalidationSize = 1;
            changeSize = 1;
            currentValue = observable.getValue();
        }

        public Generic(ObservableValue<T> observable, InvalidationListener listener0, InvalidationListener listener1) {
            super(observable);
            invalidationListeners = new InvalidationListener[]{listener0, listener1};
            invalidationSize = 2;
            currentValue = observable.getValue();
        }

        public Generic(ObservableValue<T> observable, ChangeListener<? super T> listener0, ChangeListener<? super T> listener1) {
            super(observable);
            changeListeners = new ChangeListener[]{listener0, listener1};
            changeSize = 2;
            currentValue = observable.getValue();
        }

        @Override
        protected ListenerHelper<T> addListener(InvalidationListener listener) {
            if (invalidationListeners == null) {
                invalidationListeners = new InvalidationListener[]{listener};
                invalidationSize = 1;
            } else {
                final int oldCapacity = invalidationListeners.length;
                if (locked) {
                    final int newCapacity = (invalidationSize < oldCapacity) ? oldCapacity : oldCapacity * 3 / 2 + 1;
                    invalidationListeners = Arrays.copyOf(invalidationListeners, newCapacity);
                    locked = false; // listeners changed, unlock it.
                } else if (invalidationSize == oldCapacity) {
                    final int newCapacity = oldCapacity * 3 / 2 + 1;
                    invalidationListeners = Arrays.copyOf(invalidationListeners, newCapacity);
                }
                invalidationListeners[invalidationSize++] = listener;
            }
            return this;
        }

        @Override
        protected ListenerHelper<T> removeListener(InvalidationListener listener) {
            if (invalidationListeners != null) {
                for (int i = 0; i < invalidationSize; i++) {
                    if (invalidationListeners[i].equals(listener)) {
                        if (invalidationSize == 1) {
                            if (changeSize == 1) {
                                return new SingleChange<>(observable, changeListeners[0]);
                            }
                            invalidationListeners = null;
                            invalidationSize = 0;
                        } else if (invalidationSize == 2 && changeSize == 0) {
                            return new SingleInvalidation<>(observable, invalidationListeners[1 - i]);
                        } else {
                            final int numMoved = invalidationSize - i - 1;
                            final InvalidationListener[] oldListeners = invalidationListeners;
                            if (locked) {
                                invalidationListeners = new InvalidationListener[invalidationListeners.length];
                                System.arraycopy(oldListeners, 0, invalidationListeners, 0, i);
                            }
                            if (numMoved > 0) {
                                System.arraycopy(oldListeners, i + 1, invalidationListeners, i, numMoved);
                            }
                            invalidationSize--;
                            if (locked) {
                                locked = false; // listeners changed, unlock it.
                            } else {
                                invalidationListeners[invalidationSize] = null; // release object.
                            }
                        }
                        break;
                    }
                }
            }
            return this;
        }

        @Override
        protected ListenerHelper<T> addListener(ChangeListener<? super T> listener) {
            if (changeListeners == null) {
                changeListeners = new ChangeListener[]{listener};
                changeSize = 1;
            } else {
                final int oldCapacity = changeListeners.length;
                if (locked) {
                    final int newCapacity = (changeSize < oldCapacity) ? oldCapacity : oldCapacity * 3 / 2 + 1;
                    changeListeners = Arrays.copyOf(changeListeners, newCapacity);
                    locked = false; // listeners changed, unlock it.
                } else if (changeSize == oldCapacity) {
                    final int newCapacity = oldCapacity * 3 / 2 + 1;
                    changeListeners = Arrays.copyOf(changeListeners, newCapacity);
                }
                changeListeners[changeSize++] = listener;
            }
            return this;
        }

        @Override
        protected ListenerHelper<T> removeListener(ChangeListener<? super T> listener) {
            if (changeListeners != null) {
                for (int i = 0; i < changeSize; i++) {
                    if (changeListeners[i].equals(listener)) {
                        if (changeSize == 1) {
                            if (invalidationSize == 1) {
                                return new SingleInvalidation<>(observable, invalidationListeners[0]);
                            }
                            changeListeners = null;
                            changeSize = 0;
                        } else if (changeSize == 2 && invalidationSize == 0) {
                            return new SingleChange<>(observable, changeListeners[1 - i]);
                        } else {
                            final int numMoved = changeSize - i - 1;
                            final ChangeListener<? super T>[] oldListeners = changeListeners;
                            if (locked) {
                                changeListeners = new ChangeListener[changeListeners.length];
                                System.arraycopy(oldListeners, 0, changeListeners, 0, i);
                            }
                            if (numMoved > 0) {
                                System.arraycopy(oldListeners, i + 1, changeListeners, i, numMoved);
                            }
                            changeSize--;
                            if (locked) {
                                locked = false; // listeners changed, unlock it.
                            } else {
                                changeListeners[changeSize] = null; // release object.
                            }
                        }
                        break;
                    }
                }
            }
            return this;
        }

        @Override
        protected void fire() {
            final InvalidationListener[] invalidationList = this.invalidationListeners;
            final int invalidationSize = this.invalidationSize;
            final ChangeListener<? super T>[] changeList = this.changeListeners;
            final int changeSize = this.changeSize;
            try {
                locked = true;
                for (int i = 0; i < invalidationSize; i++) {
                    try {
                        invalidationList[i].invalidated(observable);
                    } catch (RuntimeException e) {
                        final Thread currentThread = Thread.currentThread();
                        currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, e);
                    }
                }
                if (changeSize != 0) {
                    final T oldValue = currentValue;
                    currentValue = observable.getValue();
                    if (currentValue == null ? oldValue != null : !currentValue.equals(oldValue)) {
                        for (int i = 0; i < changeSize; i++) {
                            try {
                                changeList[i].onChanged(observable, oldValue, currentValue);
                            } catch (RuntimeException e) {
                                final Thread currentThread = Thread.currentThread();
                                currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, e);
                            }
                        }
                    }
                }
            } finally {
                locked = false;
            }
        }
    }
}
