package com.github.mouse0w0.observable.collection;

import java.util.Map;

public interface ObservableMap<K, V> extends Map<K, V> {

    void addListener(MapChangeListener<? super K, ? super V> listener);

    void removeListener(MapChangeListener<? super K, ? super V> listener);
}
