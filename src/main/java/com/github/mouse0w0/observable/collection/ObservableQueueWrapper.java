package com.github.mouse0w0.observable.collection;

import java.util.*;

public class ObservableQueueWrapper<E> extends AbstractQueue<E> implements ObservableQueue<E> {

    private final List<QueueChangeListener<? super E>> listeners = new LinkedList<>();

    private final Queue<E> queue;

    public ObservableQueueWrapper(Queue<E> queue) {
        this.queue = queue;
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean add(E e) {
        if (queue.add(e)) {
            notifyChanged(new AddedChange(Collections.singletonList(e)));
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (queue.remove(o)) {
            notifyChanged(new AddedChange(Collections.singletonList((E) o)));
            return true;
        }
        return false;
    }

    @Override
    public boolean offer(E e) {
        if (queue.offer(e)) {
            notifyChanged(new AddedChange(Collections.singletonList(e)));
            return true;
        }
        return false;
    }

    @Override
    public E poll() {
        E e = queue.poll();
        if (e != null) {
            notifyChanged(new RemovedChange(Collections.singletonList(e)));
        }
        return e;
    }

    @Override
    public E remove() {
        E e = queue.remove();
        notifyChanged(new RemovedChange(e));
        return e;
    }

    @Override
    public E peek() {
        return queue.peek();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (queue.addAll(c)) {
            notifyChanged(new AddedChange(new ArrayList<>(c)));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (queue.removeAll(c)) {
            notifyChanged(new RemovedChange(new ArrayList<>((Collection<E>) c)));
            return true;
        }
        return false;
    }

    @Override
    public void addChangeListener(QueueChangeListener<? super E> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeChangeListener(QueueChangeListener<? super E> listener) {
        listeners.remove(listener);
    }

    protected void notifyChanged(QueueChangeListener.Change<? super E> change) {
        for (QueueChangeListener listener : listeners) {
            listener.onChanged(change);
        }
    }

    protected class AddedChange extends QueueChangeListener.Change<E> {

        private final List<E> added;

        public AddedChange(E added) {
            this(Collections.singletonList(added));
        }

        public AddedChange(List<E> added) {
            super(ObservableQueueWrapper.this);
            this.added = added;
        }

        @Override
        public boolean wasRemoved() {
            return false;
        }

        @Override
        public boolean wasAdded() {
            return true;
        }

        @Override
        public List<E> getRemoved() {
            return Collections.emptyList();
        }

        @Override
        public List<E> getAdded() {
            return added;
        }
    }

    protected class RemovedChange extends QueueChangeListener.Change<E> {

        private final List<E> removed;

        public RemovedChange(E removed) {
            this(Collections.singletonList(removed));
        }

        public RemovedChange(List<E> removed) {
            super(ObservableQueueWrapper.this);
            this.removed = removed;
        }

        @Override
        public boolean wasRemoved() {
            return true;
        }

        @Override
        public boolean wasAdded() {
            return false;
        }

        @Override
        public List<E> getRemoved() {
            return removed;
        }

        @Override
        public List<E> getAdded() {
            return Collections.emptyList();
        }
    }
}
