package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class ReadOnlyDoubleWrapper extends SimpleDoubleValue implements ReadOnlyWrapper<Double> {

    private ReadOnlyDoubleValue readOnlyDoubleValue;

    public ReadOnlyDoubleWrapper() {
    }

    public ReadOnlyDoubleWrapper(double value) {
        super(value);
    }

    @Override
    public ObservableDoubleValue toReadOnly() {
        if (readOnlyDoubleValue == null)
            readOnlyDoubleValue = new ReadOnlyDoubleValue();
        return readOnlyDoubleValue;
    }

    @Override
    public String toString() {
        return "ReadOnlyDoubleWrapper{" +
                "value=" + get() +
                '}';
    }

    private class ReadOnlyDoubleValue implements ObservableDoubleValue {
        @Override
        public double get() {
            return ReadOnlyDoubleWrapper.this.get();
        }

        @Override
        public int intValue() {
            return ReadOnlyDoubleWrapper.this.intValue();
        }

        @Override
        public long longValue() {
            return ReadOnlyDoubleWrapper.this.longValue();
        }

        @Override
        public float floatValue() {
            return ReadOnlyDoubleWrapper.this.floatValue();
        }

        @Override
        public double doubleValue() {
            return ReadOnlyDoubleWrapper.this.doubleValue();
        }

        @Override
        public Double getValue() {
            return ReadOnlyDoubleWrapper.this.getValue();
        }

        @Override
        public void addListener(InvalidationListener listener) {
            ReadOnlyDoubleWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            ReadOnlyDoubleWrapper.this.removeListener(listener);
        }

        @Override
        public void addListener(ChangeListener<? super Double> listener) {
            ReadOnlyDoubleWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(ChangeListener<? super Double> listener) {
            ReadOnlyDoubleWrapper.this.removeListener(listener);
        }

        @Override
        public String toString() {
            return "ReadOnlyDoubleValue{" +
                    "value=" + ReadOnlyDoubleWrapper.this.get() +
                    '}';
        }
    }
}

