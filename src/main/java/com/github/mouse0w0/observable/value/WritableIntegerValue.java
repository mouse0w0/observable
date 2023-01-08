package com.github.mouse0w0.observable.value;

public interface WritableIntegerValue extends WritableNumberValue<Integer>, ObservableIntegerValue {

    void set(int value);
}
