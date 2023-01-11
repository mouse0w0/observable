package com.github.mouse0w0.observable.value;

public interface WritableStringValue extends ObservableStringValue, WritableObjectValue<String> {

    void set(String value);
}
