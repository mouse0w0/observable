package com.github.mouse0w0.observable.collection;

import java.util.*;
import java.util.function.Supplier;

public final class ObservableCollections {

    public static <E> ObservableList<E> observableArrayList() {
        return observableList(new ArrayList<>());
    }

    public static <E> ObservableList<E> observableList(Supplier<List<E>> supplier) {
        return observableList(supplier.get());
    }

    public static <E> ObservableList<E> observableList(List<E> list) {
        return list instanceof RandomAccess ? new ObservableRandomAccessListWrapper<>(list) : new ObservableListWrapper<>(list);
    }

    public static <E> ObservableList<E> unmodifiableObservableList(ObservableList<E> list) {
        return new UnmodifiableObservableList<>(list);
    }

    public static <E> ObservableSet<E> observableHashSet() {
        return observableSet(new HashSet<>());
    }

    public static <E> ObservableSet<E> observableSet(Supplier<Set<E>> set) {
        return observableSet(set.get());
    }

    public static <E> ObservableSet<E> observableSet(Set<E> set) {
        return new ObservableSetWrapper<>(set);
    }

    public static <E> ObservableSet<E> unmodifiableObservableSet(ObservableSet<E> set) {
        return new UnmodifiableObservableSet<>(set);
    }

    public static <K, V> ObservableMap<K, V> observableHashMap() {
        return observableMap(new HashMap<>());
    }

    public static <K, V> ObservableMap<K, V> observableMap(Supplier<Map<K, V>> supplier) {
        return observableMap(supplier.get());
    }

    public static <K, V> ObservableMap<K, V> observableMap(Map<K, V> map) {
        return new ObservableMapWrapper<>(map);
    }

    public static <K, V> ObservableMap<K, V> unmodifiableObservableMap(ObservableMap<K, V> map) {
        return new UnmodifiableObservableMap<>(map);
    }

    public static <E> ObservableQueue<E> observableQueue(Supplier<Queue<E>> supplier) {
        return observableQueue(supplier.get());
    }

    public static <E> ObservableQueue<E> observableQueue(Queue<E> queue) {
        return new ObservableQueueWrapper<>(queue);
    }

    public static <E> ObservableQueue<E> unmodifiableObservableQueue(ObservableQueue<E> queue) {
        return new UnmodifiableObservableQueue<>(queue);
    }

    public static <E> ObservableDeque<E> observableDeque(Supplier<Deque<E>> supplier) {
        return observableDeque(supplier.get());
    }

    public static <E> ObservableDeque<E> observableDeque(Deque<E> deque) {
        return new ObservableDequeWrapper<>(deque);
    }

    public static <E> ObservableDeque<E> unmodifiableObservableDeque(ObservableDeque<E> deque) {
        return new UnmodifiableObservableDeque<>(deque);
    }

    private static class UnmodifiableObservableList<E> extends AbstractList<E> implements ObservableList<E> {

        private final ObservableList<E> list;

        public UnmodifiableObservableList(ObservableList<E> list) {
            this.list = list;
        }

        @Override
        public void addListener(ListChangeListener<? super E> listener) {
            list.addListener(listener);
        }

        @Override
        public void removeListener(ListChangeListener<? super E> listener) {
            list.removeListener(listener);
        }

        @Override
        public E get(int index) {
            return list.get(index);
        }

        @Override
        public int size() {
            return list.size();
        }
    }

    private static class UnmodifiableObservableSet<E> extends AbstractSet<E> implements ObservableSet<E> {

        private final ObservableSet<E> set;

        public UnmodifiableObservableSet(ObservableSet<E> set) {
            this.set = set;
        }

        @Override
        public void addListener(SetChangeListener<? super E> listener) {
            set.addListener(listener);
        }

        @Override
        public void removeListener(SetChangeListener<? super E> listener) {
            set.removeListener(listener);
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                private final Iterator<? extends E> i = set.iterator();

                @Override
                public boolean hasNext() {
                    return i.hasNext();
                }

                @Override
                public E next() {
                    return i.next();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        @Override
        public int size() {
            return set.size();
        }

        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    private static class UnmodifiableObservableMap<K, V> extends AbstractMap<K, V> implements ObservableMap<K, V> {

        private final ObservableMap<K, V> map;

        public UnmodifiableObservableMap(ObservableMap<K, V> map) {
            this.map = map;
        }

        private Set<K> keyset;
        private Collection<V> values;
        private Set<Entry<K, V>> entryset;

        @Override
        public int size() {
            return map.size();
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return map.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return map.containsValue(value);
        }

        @Override
        public V get(Object key) {
            return map.get(key);
        }

        @Override
        public Set<K> keySet() {
            if (keyset == null) {
                keyset = Collections.unmodifiableSet(map.keySet());
            }
            return keyset;
        }

        @Override
        public Collection<V> values() {
            if (values == null) {
                values = Collections.unmodifiableCollection(map.values());
            }
            return values;
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            if (entryset == null) {
                entryset = Collections.unmodifiableMap(map).entrySet();
            }
            return entryset;
        }

        @Override
        public void addListener(MapChangeListener<? super K, ? super V> listener) {
            map.addListener(listener);
        }

        @Override
        public void removeListener(MapChangeListener<? super K, ? super V> listener) {
            map.removeListener(listener);
        }
    }

    private static class UnmodifiableObservableQueue<E> extends AbstractQueue<E> implements ObservableQueue<E> {

        private final ObservableQueue<E> queue;

        public UnmodifiableObservableQueue(ObservableQueue<E> queue) {
            this.queue = queue;
        }

        @Override
        public void addListener(QueueChangeListener<? super E> listener) {
            queue.addListener(listener);
        }

        @Override
        public void removeListener(QueueChangeListener<? super E> listener) {
            queue.removeListener(listener);
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
        public boolean offer(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public E poll() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E peek() {
            return queue.peek();
        }
    }

    private static class UnmodifiableObservableDeque<E> extends UnmodifiableObservableQueue<E> implements ObservableDeque<E> {

        private final ObservableDeque<E> deque;

        public UnmodifiableObservableDeque(ObservableDeque<E> deque) {
            super(deque);
            this.deque = deque;
        }

        @Override
        public void addFirst(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addLast(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean offerFirst(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean offerLast(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public E removeFirst() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E removeLast() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E pollFirst() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E pollLast() {
            throw new UnsupportedOperationException();
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
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeLastOccurrence(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void push(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public E pop() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterator<E> descendingIterator() {
            return deque.descendingIterator();
        }
    }

    private ObservableCollections() {
    }
}
