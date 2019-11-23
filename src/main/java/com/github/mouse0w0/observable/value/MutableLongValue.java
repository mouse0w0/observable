package com.github.mouse0w0.observable.value;

public interface MutableLongValue extends MutableNumberValue<Long>, ObservableLongValue {

    void set(long value);

    ObservableLongValue toUnmodifiable();
}
