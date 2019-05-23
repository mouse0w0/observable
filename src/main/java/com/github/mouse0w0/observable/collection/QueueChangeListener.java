package com.github.mouse0w0.observable.collection;

import java.util.List;

@FunctionalInterface
public interface QueueChangeListener<E> {

    void onChanged(Change<E> change);

    abstract class Change<E> {

        private final ObservableQueue<E> queue;

        public Change(ObservableQueue<E> queue) {
            this.queue = queue;
        }

        public ObservableQueue<E> getQueue() {
            return queue;
        }

        abstract public boolean wasRemoved();

        abstract public boolean wasAdded();

        abstract public List<E> getRemoved();

        abstract public List<E> getAdded();
    }
}
