package com.github.mouse0w0.observable.collection;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

public class ObservableSetWrapper<E> extends AbstractSet<E> implements ObservableSet<E> {

    private final Set<E> set;

    private SetListenerHelper<E> listenerHelper;

    public ObservableSetWrapper(Set<E> set) {
        this.set = set;
    }

    @Override
    public void addListener(SetChangeListener<? super E> listener) {
        listenerHelper = SetListenerHelper.addListener(listenerHelper, listener);
    }

    @Override
    public void removeListener(SetChangeListener<? super E> listener) {
        listenerHelper = SetListenerHelper.removeListener(listenerHelper, listener);
    }

    protected void notifyChanged(SetChangeListener.Change<? extends E> change) {
        SetListenerHelper.fire(listenerHelper, change);
    }

    @Override
    public boolean add(E e) {
        if (set.add(e)) {
            notifyChanged(new AddedChange(e));
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (set.remove(o)) {
            notifyChanged(new RemovedChange((E) o));
            return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            Iterator<E> iterator = set.iterator();
            E lastElement;

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public E next() {
                lastElement = iterator.next();
                return lastElement;
            }

            @Override
            public void remove() {
                iterator.remove();
                notifyChanged(new RemovedChange(lastElement));
            }
        };
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public String toString() {
        return set.toString();
    }

    private class AddedChange extends SetChangeListener.Change<E> {

        private final E element;

        public AddedChange(E element) {
            super(ObservableSetWrapper.this);
            this.element = element;
        }

        @Override
        public boolean wasAdded() {
            return true;
        }

        @Override
        public boolean wasRemoved() {
            return false;
        }

        @Override
        public E getAddedElement() {
            return element;
        }

        @Override
        public E getRemovedElement() {
            return null;
        }
    }

    private class RemovedChange extends SetChangeListener.Change<E> {

        private final E element;

        private RemovedChange(E element) {
            super(ObservableSetWrapper.this);
            this.element = element;
        }

        @Override
        public boolean wasAdded() {
            return false;
        }

        @Override
        public boolean wasRemoved() {
            return true;
        }

        @Override
        public E getAddedElement() {
            return null;
        }

        @Override
        public E getRemovedElement() {
            return element;
        }
    }
}
