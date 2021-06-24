package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class NonNullStringValue extends SimpleStringValue {

    private final String defaultValue;

    public NonNullStringValue() {
        this("");
    }

    public NonNullStringValue(String defaultValue) {
        this.defaultValue = Objects.requireNonNull(defaultValue, "Default value cannot be null");
        set(defaultValue);
    }

    @Override
    public void set(String value) {
        super.set(value == null ? defaultValue : value);
    }
}
