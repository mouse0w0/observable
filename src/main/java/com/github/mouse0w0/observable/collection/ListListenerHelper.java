package com.github.mouse0w0.observable.collection;

import com.github.mouse0w0.observable.InvalidationListener;

import java.util.Arrays;

abstract class ListListenerHelper<E> {
    public static <E> ListListenerHelper<E> addListener(ObservableList<E> observable, ListListenerHelper<E> helper, InvalidationListener listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? new SingleInvalidation<>(observable, listener) : helper.addListener(listener);
    }

    public static <E> ListListenerHelper<E> removeListener(ListListenerHelper<E> helper, InvalidationListener listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? null : helper.removeListener(listener);
    }

    public static <E> ListListenerHelper<E> addListener(ObservableList<E> observable, ListListenerHelper<E> helper, ListChangeListener<? super E> listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? new SingleChange<>(observable, listener) : helper.addListener(listener);
    }

    public static <E> ListListenerHelper<E> removeListener(ListListenerHelper<E> helper, ListChangeListener<? super E> listener) {
        if (listener == null) throw new NullPointerException("listener");
        return helper == null ? null : helper.removeListener(listener);
    }

    public static <E> void fire(ListListenerHelper<E> helper, ListChangeListener.Change<? extends E> change) {
        if (helper != null) {
            helper.fire(change);
        }
    }

    protected final ObservableList<E> observable;

    public ListListenerHelper(ObservableList<E> observable) {
        this.observable = observable;
    }

    protected abstract ListListenerHelper<E> addListener(InvalidationListener listener);

    protected abstract ListListenerHelper<E> removeListener(InvalidationListener listener);

    protected abstract ListListenerHelper<E> addListener(ListChangeListener<? super E> listener);

    protected abstract ListListenerHelper<E> removeListener(ListChangeListener<? super E> listener);

    protected abstract void fire(ListChangeListener.Change<? extends E> change);

    private static final class SingleInvalidation<E> extends ListListenerHelper<E> {
        private final InvalidationListener listener;

        public SingleInvalidation(ObservableList<E> observable, InvalidationListener listener) {
            super(observable);
            this.listener = listener;
        }

        @Override
        protected ListListenerHelper<E> addListener(InvalidationListener listener) {
            return new Generic<>(observable, this.listener, listener);
        }

        @Override
        protected ListListenerHelper<E> removeListener(InvalidationListener listener) {
            return this.listener.equals(listener) ? null : this;
        }

        @Override
        protected ListListenerHelper<E> addListener(ListChangeListener<? super E> listener) {
            return new Generic<>(observable, this.listener, listener);
        }

        @Override
        protected ListListenerHelper<E> removeListener(ListChangeListener<? super E> listener) {
            return this;
        }

        @Override
        protected void fire(ListChangeListener.Change<? extends E> change) {
            listener.invalidated(observable);
        }
    }

    private static final class SingleChange<E> extends ListListenerHelper<E> {
        private final ListChangeListener<? super E> listener;

        public SingleChange(ObservableList<E> observable, ListChangeListener<? super E> listener) {
            super(observable);
            this.listener = listener;
        }

        @Override
        protected ListListenerHelper<E> addListener(InvalidationListener listener) {
            return new Generic<>(observable, listener, this.listener);
        }

        @Override
        protected ListListenerHelper<E> removeListener(InvalidationListener listener) {
            return this;
        }

        @Override
        protected ListListenerHelper<E> addListener(ListChangeListener<? super E> listener) {
            return new Generic<>(observable, this.listener, listener);
        }

        @Override
        protected ListListenerHelper<E> removeListener(ListChangeListener<? super E> listener) {
            return this.listener.equals(listener) ? null : this;
        }

        @Override
        protected void fire(ListChangeListener.Change<? extends E> change) {
            try {
                listener.onChanged(change);
            } catch (RuntimeException e) {
                final Thread currentThread = Thread.currentThread();
                currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, e);
            }
        }
    }

    private static final class Generic<E> extends ListListenerHelper<E> {
        private InvalidationListener[] invalidationListeners;
        private ListChangeListener<? super E>[] changeListeners;
        private int invalidationSize;
        private int changeSize;
        private boolean locked;

        public Generic(ObservableList<E> observable, InvalidationListener invalidationListener, ListChangeListener<? super E> changeListener) {
            super(observable);
            invalidationListeners = new InvalidationListener[]{invalidationListener};
            changeListeners = new ListChangeListener[]{changeListener};
            invalidationSize = 1;
            changeSize = 1;
        }

        public Generic(ObservableList<E> observable, InvalidationListener listener0, InvalidationListener listener1) {
            super(observable);
            invalidationListeners = new InvalidationListener[]{listener0, listener1};
            invalidationSize = 2;
        }

        public Generic(ObservableList<E> observable, ListChangeListener<? super E> listener0, ListChangeListener<? super E> listener1) {
            super(observable);
            changeListeners = new ListChangeListener[]{listener0, listener1};
            changeSize = 2;
        }

        @Override
        protected ListListenerHelper<E> addListener(InvalidationListener listener) {
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
        protected ListListenerHelper<E> removeListener(InvalidationListener listener) {
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
        protected ListListenerHelper<E> addListener(ListChangeListener<? super E> listener) {
            if (changeListeners == null) {
                changeListeners = new ListChangeListener[]{listener};
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
        protected ListListenerHelper<E> removeListener(ListChangeListener<? super E> listener) {
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
                            final ListChangeListener<? super E>[] oldListeners = changeListeners;
                            if (locked) {
                                changeListeners = new ListChangeListener[changeListeners.length];
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
        protected void fire(ListChangeListener.Change<? extends E> change) {
            final InvalidationListener[] invalidationList = this.invalidationListeners;
            final int invalidationSize = this.invalidationSize;
            final ListChangeListener<? super E>[] changeList = this.changeListeners;
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
                for (int i = 0; i < changeSize; i++) {
                    try {
                        changeList[i].onChanged(change);
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