package com.github.mouse0w0.observable.value;

public class SimpleLongValue extends ObservableValueBase<Long> implements WritableLongValue {
    private long value;

    public SimpleLongValue() {
    }

    public SimpleLongValue(long value) {
        this.value = value;
    }

    @Override
    public long get() {
        valid = true;
        return value;
    }

    @Override
    public void set(long value) {
        if (this.value != value) {
            this.value = value;
            invalidate();
        }
    }

    @Override
    public void setValue(Long value) {
        set(value);
    }

    @Override
    public String toString() {
        return "SimpleLongValue{" +
                "value=" + value +
                '}';
    }
}
