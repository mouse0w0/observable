package com.github.mouse0w0.observable.value;

public interface WritableDoubleValue extends WritableNumberValue<Double>, ObservableDoubleValue {

    void set(double value);
}
