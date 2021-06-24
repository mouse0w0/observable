package com.github.mouse0w0.observable.value;

public interface MutableStringValue extends ObservableStringValue, MutableObjectValue<String> {

    void set(String value);
}
