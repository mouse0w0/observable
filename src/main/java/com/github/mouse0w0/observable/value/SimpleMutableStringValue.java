package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleMutableStringValue extends ObservableValueBase<String> implements MutableStringValue {

    private String value;
    private ImmutableStringValue immutableValue;

    public SimpleMutableStringValue() {
    }

    public SimpleMutableStringValue(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return getValue();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        if (Objects.equals(this.value, value)) {
            return;
        }
        String oldValue = this.value;
        this.value = value;
        fireValueChangeEvent(oldValue, value);
    }

    @Override
    public ObservableStringValue toImmutable() {
        if (immutableValue == null) {
            immutableValue = new ImmutableStringValue();
        }
        return immutableValue;
    }

    @Override
    public void set(String value) {
        setValue(value);
    }

    private class ImmutableStringValue implements ObservableStringValue {
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
    }
}
