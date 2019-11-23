package com.github.mouse0w0.observable.value;

public interface MutableIntValue extends MutableNumberValue<Integer>, ObservableIntValue {

    void set(int value);

    ObservableIntValue toUnmodifiable();
}
