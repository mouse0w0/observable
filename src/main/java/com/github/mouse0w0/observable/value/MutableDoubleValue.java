package com.github.mouse0w0.observable.value;

public interface MutableDoubleValue extends MutableNumberValue<Double>, ObservableDoubleValue {

    void set(double value);

    ObservableDoubleValue toUnmodifiable();
}
