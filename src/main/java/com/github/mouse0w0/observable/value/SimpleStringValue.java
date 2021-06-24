package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class SimpleStringValue extends ObservableValueBase<String> implements MutableStringValue {
    private String value;

    public SimpleStringValue() {
    }

    public SimpleStringValue(String value) {
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
        return "SimpleStringValue{" +
                "value='" + value + '\'' +
                '}';
    }
}
