package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class ReadOnlyLongWrapper extends SimpleLongValue implements WritableValue<Long> {

    private ReadOnlyLongValue readOnlyLongValue;

    public ReadOnlyLongWrapper() {
    }

    public ReadOnlyLongWrapper(long value) {
        super(value);
    }

    public ObservableLongValue toReadOnly() {
        if (readOnlyLongValue == null)
            readOnlyLongValue = new ReadOnlyLongValue();
        return readOnlyLongValue;
    }

    private class ReadOnlyLongValue implements ObservableLongValue {
        @Override
        public long get() {
            return ReadOnlyLongWrapper.this.get();
        }

        @Override
        public int intValue() {
            return ReadOnlyLongWrapper.this.intValue();
        }

        @Override
        public long longValue() {
            return ReadOnlyLongWrapper.this.longValue();
        }

        @Override
        public float floatValue() {
            return ReadOnlyLongWrapper.this.floatValue();
        }

        @Override
        public double doubleValue() {
            return ReadOnlyLongWrapper.this.doubleValue();
        }

        @Override
        public Long getValue() {
            return ReadOnlyLongWrapper.this.getValue();
        }

        @Override
        public void addListener(InvalidationListener listener) {
            ReadOnlyLongWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            ReadOnlyLongWrapper.this.removeListener(listener);
        }

        @Override
        public void addListener(ChangeListener<? super Long> listener) {
            ReadOnlyLongWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(ChangeListener<? super Long> listener) {
            ReadOnlyLongWrapper.this.removeListener(listener);
        }

        @Override
        public String toString() {
            return "ReadOnlyLongValue{" +
                    "value=" + ReadOnlyLongWrapper.this.get() +
                    '}';
        }
    }
}
