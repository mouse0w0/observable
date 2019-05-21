package com.github.mouse0w0.observable.value;

public interface MutableFloatValue extends MutableNumberValue<Float>, ObservableFloatValue {

    void set(float value);

    ObservableFloatValue toImmutable();
}
