package com.github.mouse0w0.observable.value;

public class SimpleIntValue extends ObservableValueBase<Integer> implements WritableIntValue {
    private int value;

    public SimpleIntValue() {
    }

    public SimpleIntValue(int value) {
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
