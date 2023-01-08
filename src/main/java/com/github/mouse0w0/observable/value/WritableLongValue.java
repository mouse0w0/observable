package com.github.mouse0w0.observable.value;

public interface WritableLongValue extends WritableNumberValue<Long>, ObservableLongValue {

    void set(long value);
}
