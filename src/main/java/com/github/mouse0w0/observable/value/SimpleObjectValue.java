package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleObjectValue<T> extends ObservableValueBase<T> implements MutableObjectValue<T> {
    private T value;

    public SimpleObjectValue() {
    }

    public SimpleObjectValue(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T getValue() {
        return get();
    }

    @Override
    public void setValue(T value) {
        set(value);
    }

    @Override
    public void set(T value) {
        if (Objects.equals(this.value, value)) {
            return;
        }
        T oldValue = this.value;
        this.value = value;
        fireValueChangedEvent(oldValue, value);
    }

    @Override
    public String toString() {
        return "SimpleObjectValue{" +
                "value=" + value +
                '}';
    }
}
