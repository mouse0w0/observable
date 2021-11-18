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

        public abstract boolean wasRemoved();

        public abstract boolean wasAdded();

        public abstract List<E> getRemoved();

        public abstract List<E> getAdded();
    }
}
