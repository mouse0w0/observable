package com.github.mouse0w0.observable.value;

@FunctionalInterface
public interface ChangeListener<T> {

    void onChanged(ObservableValue<? extends T> observable, T oldValue, T newValue);
}
