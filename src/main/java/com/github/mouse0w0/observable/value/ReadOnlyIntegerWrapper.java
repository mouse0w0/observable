package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class ReadOnlyIntegerWrapper extends SimpleIntegerValue implements WritableValue<Integer> {

    private ReadOnlyIntegerValue readOnlyIntValue;

    public ReadOnlyIntegerWrapper() {
    }

    public ReadOnlyIntegerWrapper(int value) {
        super(value);
    }

    public ObservableIntegerValue toReadOnly() {
        if (readOnlyIntValue == null)
            readOnlyIntValue = new ReadOnlyIntegerValue();
        return readOnlyIntValue;
    }

    @Override
    public String toString() {
        return "ReadOnlyIntegerWrapper{" +
                "value=" + get() +
                '}';
    }

    private class ReadOnlyIntegerValue implements ObservableIntegerValue {
        @Override
        public int get() {
            return ReadOnlyIntegerWrapper.this.get();
        }

        @Override
        public int intValue() {
            return ReadOnlyIntegerWrapper.this.intValue();
        }

        @Override
        public long longValue() {
            return ReadOnlyIntegerWrapper.this.longValue();
        }

        @Override
        public float floatValue() {
            return ReadOnlyIntegerWrapper.this.floatValue();
        }

        @Override
        public double doubleValue() {
            return ReadOnlyIntegerWrapper.this.doubleValue();
        }

        @Override
        public Integer getValue() {
            return ReadOnlyIntegerWrapper.this.getValue();
        }

        @Override
        public void addListener(InvalidationListener listener) {
            ReadOnlyIntegerWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            ReadOnlyIntegerWrapper.this.removeListener(listener);
        }

        @Override
        public void addListener(ChangeListener<? super Integer> listener) {
            ReadOnlyIntegerWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(ChangeListener<? super Integer> listener) {
            ReadOnlyIntegerWrapper.this.removeListener(listener);
        }

        @Override
        public String toString() {
            return "ReadOnlyIntValue{" +
                    "value=" + ReadOnlyIntegerWrapper.this.get() +
                    '}';
        }
    }
}
