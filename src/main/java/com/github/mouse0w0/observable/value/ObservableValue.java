package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.Observable;

public interface ObservableValue<T> extends Observable {

    void addListener(ChangeListener<? super T> listener);

    void removeListener(ChangeListener<? super T> listener);

    T getValue();
}
