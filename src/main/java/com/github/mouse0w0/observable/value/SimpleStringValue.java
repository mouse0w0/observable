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
        if (this.value != value) {
            this.value = value;
            notifyChanged();
        }
    }

    @Override
    public String toString() {
        return "SimpleStringValue{" +
                "value='" + value + '\'' +
                '}';
    }
}
