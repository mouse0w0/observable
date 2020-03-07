package com.github.mouse0w0.observable.value;

import java.util.Objects;

public class NonNullMutableStringValue extends SimpleMutableStringValue {

    private final String defaultValue;

    public NonNullMutableStringValue() {
        this("");
    }

    public NonNullMutableStringValue(String defaultValue) {
        this.defaultValue = Objects.requireNonNull(defaultValue, "Default value cannot be null");
        set(defaultValue);
    }

    @Override
    public void set(String value) {
        super.set(value == null ? defaultValue : value);
    }
}
