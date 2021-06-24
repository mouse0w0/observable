package com.github.mouse0w0.observable.value;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class ObservableValueBase<T> implements ObservableValue<T> {

    private List<ValueChangeListener<? super T>> changeListeners;

    @Override
    public void addListener(ValueChangeListener<? super T> listener) {
        if (changeListeners == null) changeListeners = new LinkedList<>();
        Objects.requireNonNull(listener);
        changeListeners.add(listener);
    }

    @Override
    public void removeListener(ValueChangeListener<? super T> listener) {
        if (changeListeners == null) return;
        Objects.requireNonNull(listener);
        changeListeners.remove(listener);
    }

    protected void fireValueChangedEvent(T oldValue, T newValue) {
        if (changeListeners == null) return;
        for (ValueChangeListener<? super T> listener : changeListeners) {
            listener.onChanged(this, oldValue, newValue);
        }
    }
}
