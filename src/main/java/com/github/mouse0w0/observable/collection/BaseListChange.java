package com.github.mouse0w0.observable.collection;

import java.util.Collections;
import java.util.List;

abstract class BaseListChange<E> extends ListChangeListener.Change<E> {

    private final int from;
    private final int to;

    public BaseListChange(ObservableList<E> list, int from, int to) {
        super(list);
        this.from = from;
        this.to = to;
    }

    @Override
    public int getFrom() {
        return from;
    }

    @Override
    public int getTo() {
        return to;
    }

    static class AddedChange<E> extends BaseListChange<E> {

        private final List<E> added;

        public AddedChange(ObservableList<E> list, E added, int from, int to) {
            this(list, Collections.singletonList(added), from, to);
        }

        public AddedChange(ObservableList<E> list, List<E> added, int from, int to) {
            super(list, from, to);
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
        public boolean wasSorted() {
            return false;
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

    static class RemovedChange<E> extends BaseListChange<E> {

        private final List<E> removed;

        public RemovedChange(ObservableList<E> list, E removed, int from, int to) {
            this(list, Collections.singletonList(removed), from, to);
        }

        public RemovedChange(ObservableList<E> list, List<E> removed, int from, int to) {
            super(list, from, to);
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
        public boolean wasSorted() {
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

    static class ReplacedChange<E> extends BaseListChange<E> {

        private final List<E> added;
        private final List<E> removed;

        public ReplacedChange(ObservableList<E> list, E added, E removed, int from, int to) {
            this(list, Collections.singletonList(added), Collections.singletonList(removed), from, to);
        }

        public ReplacedChange(ObservableList<E> list, List<E> added, List<E> removed, int from, int to) {
            super(list, from, to);
            this.added = added;
            this.removed = removed;
        }

        @Override
        public boolean wasRemoved() {
            return true;
        }

        @Override
        public boolean wasAdded() {
            return true;
        }

        @Override
        public boolean wasSorted() {
            return false;
        }

        @Override
        public List<E> getRemoved() {
            return removed;
        }

        @Override
        public List<E> getAdded() {
            return added;
        }
    }

    static class SortedChange<E> extends BaseListChange<E> {

        public SortedChange(ObservableList<E> list, int from, int to) {
            super(list, from, to);
        }

        @Override
        public boolean wasRemoved() {
            return false;
        }

        @Override
        public boolean wasAdded() {
            return false;
        }

        @Override
        public boolean wasSorted() {
            return true;
        }

        @Override
        public List<E> getRemoved() {
            return Collections.emptyList();
        }

        @Override
        public List<E> getAdded() {
            return Collections.emptyList();
        }
    }
}
