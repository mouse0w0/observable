package com.github.mouse0w0.observable.value;

public interface ObservableStringValue extends ObservableObjectValue<String> {

    String get();

    @Override
    default String getValue() {
        return get();
    }
}
