package com.github.mouse0w0.observable.value;

public class SimpleStringValue extends ObservableValueBase<String> implements WritableStringValue {
    private String value;

    public SimpleStringValue() {
    }

    public SimpleStringValue(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        valid = true;
        return value;
    }

    @Override
    public void set(String value) {
        if (this.value != value) {
            this.value = value;
            invalidate();
        }
    }

    @Override
    public void setValue(String value) {
        set(value);
    }

    @Override
    public String toString() {
        return "SimpleStringValue{" +
                "value='" + value + '\'' +
                '}';
    }
}
