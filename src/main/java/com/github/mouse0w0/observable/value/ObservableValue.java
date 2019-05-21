package com.github.mouse0w0.observable.value;

public interface ObservableValue<T> {

    T getValue();

    void addChangeListener(ValueChangeListener<? super T> listener);

    void removeChangeListener(ValueChangeListener<? super T> listener);
}
