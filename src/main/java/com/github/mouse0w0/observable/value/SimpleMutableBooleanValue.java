package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleMutableBooleanValue extends ObservableValueBase<Boolean> implements MutableBooleanValue {

    private boolean value;
    private UnmodifiableBooleanValue unmodifiableBooleanValue;

    public SimpleMutableBooleanValue() {
    }

    public SimpleMutableBooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public void setValue(Boolean value) {
        Objects.requireNonNull(value);
        set(value);
    }

    @Override
    public ObservableBooleanValue toUnmodifiable() {
        if (unmodifiableBooleanValue == null)
            unmodifiableBooleanValue = new UnmodifiableBooleanValue();
        return unmodifiableBooleanValue;
    }

    @Override
    public boolean get() {
        return value;
    }

    @Override
    public Boolean getValue() {
        return get();
    }

    @Override
    public void set(boolean value) {
        if (this.value == value) {
            return;
        }
        boolean oldValue = this.value;
        this.value = value;
        fireValueChangeEvent(oldValue, value);
    }

    @Override
    public String toString() {
        return "SimpleMutableBooleanValue{" +
                "value=" + value +
                '}';
    }

    private class UnmodifiableBooleanValue implements ObservableBooleanValue {
        @Override
        public boolean get() {
            return SimpleMutableBooleanValue.this.get();
        }

        @Override
        public Boolean getValue() {
            return SimpleMutableBooleanValue.this.getValue();
        }

        @Override
        public void addChangeListener(ValueChangeListener<? super Boolean> listener) {
            SimpleMutableBooleanValue.this.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(ValueChangeListener<? super Boolean> listener) {
            SimpleMutableBooleanValue.this.removeChangeListener(listener);
        }

        @Override
        public String toString() {
            return "UnmodifiableBooleanValue{" +
                    "value=" + value +
                    '}';
        }
    }
}
