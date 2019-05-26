package com.github.mouse0w0.observable.collection;

import java.util.Deque;
import java.util.Iterator;

public class ObservableDequeWrapper<E> extends ObservableQueueWrapper<E> implements ObservableDeque<E> {

    private final Deque<E> deque;

    public ObservableDequeWrapper(Deque<E> deque) {
        super(deque);
        this.deque = deque;
    }

    @Override
    public void addFirst(E e) {
        deque.addFirst(e);
        notifyChanged(new AddedChange(e));
    }

    @Override
    public void addLast(E e) {
        deque.addLast(e);
        notifyChanged(new AddedChange(e));
    }

    @Override
    public boolean offerFirst(E e) {
        if (deque.offerFirst(e)) {
            notifyChanged(new AddedChange(e));
            return true;
        }
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        if (deque.offerLast(e)) {
            notifyChanged(new AddedChange(e));
            return true;
        }
        return false;
    }

    @Override
    public E removeFirst() {
        E e = deque.removeFirst();
        notifyChanged(new RemovedChange(e));
        return e;
    }

    @Override
    public E removeLast() {
        E e = deque.removeLast();
        notifyChanged(new RemovedChange(e));
        return e;
    }

    @Override
    public E pollFirst() {
        E e = deque.pollFirst();
        if (e != null) {
            notifyChanged(new RemovedChange(e));
        }
        return e;
    }

    @Override
    public E pollLast() {
        E e = deque.pollLast();
        if (e != null) {
            notifyChanged(new RemovedChange(e));
        }
        return e;
    }

    @Override
    public E getFirst() {
        return deque.getFirst();
    }

    @Override
    public E getLast() {
        return deque.getLast();
    }

    @Override
    public E peekFirst() {
        return deque.peekFirst();
    }

    @Override
    public E peekLast() {
        return deque.peekLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        if (deque.removeFirstOccurrence(o)) {
            notifyChanged(new RemovedChange((E) o));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (deque.removeLastOccurrence(o)) {
            notifyChanged(new RemovedChange((E) o));
            return true;
        }
        return false;
    }

    @Override
    public void push(E e) {
        deque.push(e);
        notifyChanged(new AddedChange(e));
    }

    @Override
    public E pop() {
        E e = deque.pop();
        if (e != null) {
            notifyChanged(new RemovedChange(e));
        }
        return e;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return deque.descendingIterator();
    }
}
