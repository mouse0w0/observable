package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleMutableObjectValue<T> extends ObservableValueBase<T> implements MutableObjectValue<T> {

    private T value;
    private UnmodifiableObjectValue unmodifiableObjectValue;

    public SimpleMutableObjectValue() {
    }

    public SimpleMutableObjectValue(T value) {
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
    public ObservableObjectValue<T> toUnmodifiable() {
        if (unmodifiableObjectValue == null) {
            unmodifiableObjectValue = new UnmodifiableObjectValue();
        }
        return unmodifiableObjectValue;
    }

    @Override
    public void set(T value) {
        if (Objects.equals(this.value, value)) {
            return;
        }
        T oldValue = this.value;
        this.value = value;
        fireValueChangeEvent(oldValue, value);
    }

    @Override
    public String toString() {
        return "SimpleMutableObjectValue{" +
                "value=" + value +
                '}';
    }

    private class UnmodifiableObjectValue implements ObservableObjectValue<T> {
        @Override
        public T getValue() {
            return SimpleMutableObjectValue.this.getValue();
        }

        @Override
        public void addChangeListener(ValueChangeListener<? super T> listener) {
            SimpleMutableObjectValue.this.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(ValueChangeListener<? super T> listener) {
            SimpleMutableObjectValue.this.removeChangeListener(listener);
        }

        @Override
        public T get() {
            return getValue();
        }

        @Override
        public String toString() {
            return "UnmodifiableObjectValue{" +
                    "value=" + value +
                    '}';
        }
    }
}
