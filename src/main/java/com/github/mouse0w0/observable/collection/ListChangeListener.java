package com.github.mouse0w0.observable.collection;

import java.util.List;

@FunctionalInterface
public interface ListChangeListener<E> {

    void onChanged(Change<? extends E> change);

    abstract class Change<E> {

        private final ObservableList<E> list;

        public Change(ObservableList<E> list) {
            this.list = list;
        }

        public ObservableList<E> getList() {
            return list;
        }

        public abstract int getFrom();

        public abstract int getTo();

        public abstract boolean wasRemoved();

        public abstract boolean wasAdded();

        public abstract boolean wasSorted();

        public abstract List<E> getRemoved();

        public abstract List<E> getAdded();
    }

}
