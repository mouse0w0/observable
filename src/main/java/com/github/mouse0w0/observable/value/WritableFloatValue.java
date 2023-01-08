package com.github.mouse0w0.observable.value;

public interface WritableFloatValue extends WritableNumberValue<Float>, ObservableFloatValue {

    void set(float value);
}
