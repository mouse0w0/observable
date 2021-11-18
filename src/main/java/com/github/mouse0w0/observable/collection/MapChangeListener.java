package com.github.mouse0w0.observable.collection;

@FunctionalInterface
public interface MapChangeListener<K, V> {

    void onChanged(Change<? extends K, ? extends V> change);

    abstract class Change<K, V> {

        private final ObservableMap<K, V> map;

        public Change(ObservableMap<K, V> map) {
            this.map = map;
        }

        public ObservableMap<K, V> getMap() {
            return map;
        }

        public abstract boolean wasAdded();

        public abstract boolean wasRemoved();

        public abstract K getKey();

        public abstract V getAddedValue();

        public abstract V getRemovedValue();
    }
}
