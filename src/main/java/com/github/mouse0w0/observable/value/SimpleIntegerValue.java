package com.github.mouse0w0.observable.value;

public class SimpleIntegerValue extends ObservableValueBase<Integer> implements WritableIntegerValue {
    private int value;

    public SimpleIntegerValue() {
    }

    public SimpleIntegerValue(int value) {
        this.value = value;
    }

    @Override
    public int get() {
        valid = true;
        return value;
    }

    @Override
    public void set(int value) {
        if (this.value != value) {
            this.value = value;
            invalidate();
        }
    }

    @Override
    public void setValue(Integer value) {
        set(value);
    }

    @Override
    public String toString() {
        return "SimpleIntValue{" +
                "value=" + value +
                '}';
    }
}
