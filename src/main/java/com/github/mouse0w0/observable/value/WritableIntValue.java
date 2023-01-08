package com.github.mouse0w0.observable.value;

public interface WritableIntValue extends WritableNumberValue<Integer>, ObservableIntValue {

    void set(int value);
}
