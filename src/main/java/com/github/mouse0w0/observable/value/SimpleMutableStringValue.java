package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleMutableStringValue extends ObservableValueBase<String> implements MutableStringValue {

    private String value;
    private UnmodifiableStringValue unmodifiableStringValue;

    public SimpleMutableStringValue() {
    }

    public SimpleMutableStringValue(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }

    @Override
    public String getValue() {
        return get();
    }

    @Override
    public void setValue(String value) {
        set(value);
    }

    @Override
    public ObservableStringValue toUnmodifiable() {
        if (unmodifiableStringValue == null) {
            unmodifiableStringValue = new UnmodifiableStringValue();
        }
        return unmodifiableStringValue;
    }

    @Override
    public void set(String value) {
        if (Objects.equals(this.value, value)) {
            return;
        }
        String oldValue = this.value;
        this.value = value;
        fireValueChangedEvent(oldValue, value);
    }

    @Override
    public String toString() {
        return "SimpleMutableStringValue{" +
                "value='" + value + '\'' +
                '}';
    }

    private class UnmodifiableStringValue implements ObservableStringValue {
        @Override
        public String getValue() {
            return SimpleMutableStringValue.this.getValue();
        }

        @Override
        public void addChangeListener(ValueChangeListener<? super String> listener) {
            SimpleMutableStringValue.this.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(ValueChangeListener<? super String> listener) {
            SimpleMutableStringValue.this.removeChangeListener(listener);
        }

        @Override
        public String get() {
            return getValue();
        }

        @Override
        public String toString() {
            return "UnmodifiableStringValue{" +
                    "value=" + value +
                    '}';
        }
    }
}
