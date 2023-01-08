package com.github.mouse0w0.observable.collection;

import com.github.mouse0w0.observable.InvalidationListener;

import java.util.*;
import java.util.function.UnaryOperator;

public class ObservableListWrapper<E> extends AbstractList<E> implements ObservableList<E> {

    private final List<E> list;

    private ListListenerHelper<E> listenerHelper;

    public ObservableListWrapper(List<E> list) {
        this.list = list;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        listenerHelper = ListListenerHelper.addListener(this, listenerHelper, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listenerHelper = ListListenerHelper.removeListener(listenerHelper, listener);
    }


    @Override
    public void addListener(ListChangeListener<? super E> listener) {
        listenerHelper = ListListenerHelper.addListener(this, listenerHelper, listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super E> listener) {
        listenerHelper = ListListenerHelper.removeListener(listenerHelper, listener);
    }

    @Override
    public E set(int index, E element) {
        E removed = list.set(index, element);
        ListChangeListener.Change<? extends E> change = new BaseListChange.ReplacedChange<>(this, element, removed, index, index + 1);
        ListListenerHelper.fire(listenerHelper, change);
        return removed;
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        ListChangeListener.Change<? extends E> change = new BaseListChange.AddedChange<>(this, element, index, index + 1);
        ListListenerHelper.fire(listenerHelper, change);
    }

    @Override
    public E remove(int index) {
        E element = list.remove(index);
        ListChangeListener.Change<? extends E> change = new BaseListChange.RemovedChange<>(this, element, index, index + 1);
        ListListenerHelper.fire(listenerHelper, change);
        return element;
    }

    @Override
    public void clear() {
        List<E> removed = new ArrayList<>(list);
        list.clear();
        ListChangeListener.Change<? extends E> change = new BaseListChange.RemovedChange<>(this, removed, 0, removed.size());
        ListListenerHelper.fire(listenerHelper, change);
    }

    @Override
    public void sort(Comparator<? super E> c) {
        list.sort(c);
        ListChangeListener.Change<? extends E> change = new BaseListChange.SortedChange<>(this, 0, size());
        ListListenerHelper.fire(listenerHelper, change);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new SubList(super.subList(fromIndex, toIndex));
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    private class SubList implements List<E> {

        private final List<E> list;

        private SubList(List<E> list) {
            this.list = list;
        }

        @Override
        public int size() {
            return list.size();
        }

        @Override
        public boolean isEmpty() {
            return list.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return list.contains(o);
        }

        @Override
        public Iterator<E> iterator() {
            return list.iterator();
        }

        @Override
        public Object[] toArray() {
            return list.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return list.toArray(a);
        }

        @Override
        public boolean add(E e) {
            return list.add(e);
        }

        @Override
        public boolean remove(Object o) {
            return list.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return list.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return list.addAll(c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            return list.addAll(index, c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return list.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return list.retainAll(c);
        }

        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            list.replaceAll(operator);
        }

        @Override
        public void sort(Comparator<? super E> c) {
            list.sort(c);
        }

        @Override
        public void clear() {
            list.clear();
        }

        @Override
        public boolean equals(Object o) {
            return list.equals(o);
        }

        @Override
        public int hashCode() {
            return list.hashCode();
        }

        @Override
        public E get(int index) {
            return list.get(index);
        }

        @Override
        public E set(int index, E element) {
            return list.set(index, element);
        }

        @Override
        public void add(int index, E element) {
            list.add(index, element);
        }

        @Override
        public E remove(int index) {
            return list.remove(index);
        }

        @Override
        public int indexOf(Object o) {
            return list.indexOf(o);
        }

        @Override
        public int lastIndexOf(Object o) {
            return list.lastIndexOf(o);
        }

        @Override
        public ListIterator<E> listIterator() {
            return list.listIterator();
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return list.listIterator(index);
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return new SubList(list.subList(fromIndex, toIndex));
        }

        @Override
        public Spliterator<E> spliterator() {
            return list.spliterator();
        }
    }
}
