package com.github.mouse0w0.observable.value;

public class SimpleObjectValue<T> extends ObservableValueBase<T> implements WritableObjectValue<T> {
    private T value;

    public SimpleObjectValue() {
    }

    public SimpleObjectValue(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        valid = true;
        return value;
    }

    @Override
    public void set(T value) {
        if (this.value != value) {
            this.value = value;
            invalidate();
        }
    }

    @Override
    public void setValue(T value) {
        set(value);
    }

    @Override
    public String toString() {
        return "SimpleObjectValue{" +
                "value=" + value +
                '}';
    }
}
