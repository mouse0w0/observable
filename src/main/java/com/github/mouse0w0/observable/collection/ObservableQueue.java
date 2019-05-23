package com.github.mouse0w0.observable.collection;

import java.util.Queue;

public interface ObservableQueue<E> extends Queue<E> {

    void addChangeListener(QueueChangeListener<? super E> listener);

    void removeChangeListener(QueueChangeListener<? super E> listener);
}
