package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class ReadOnlyIntWrapper extends SimpleIntValue implements ReadOnlyWrapper<Integer> {

    private ReadOnlyIntValue readOnlyIntValue;

    public ReadOnlyIntWrapper() {
    }

    public ReadOnlyIntWrapper(int value) {
        super(value);
    }

    @Override
    public ObservableIntValue toReadOnly() {
        if (readOnlyIntValue == null)
            readOnlyIntValue = new ReadOnlyIntValue();
        return readOnlyIntValue;
    }

    @Override
    public String toString() {
        return "ReadOnlyIntWrapper{" +
                "value=" + get() +
                '}';
    }

    private class ReadOnlyIntValue implements ObservableIntValue {
        @Override
        public int get() {
            return ReadOnlyIntWrapper.this.get();
        }

        @Override
        public int intValue() {
            return ReadOnlyIntWrapper.this.intValue();
        }

        @Override
        public long longValue() {
            return ReadOnlyIntWrapper.this.longValue();
        }

        @Override
        public float floatValue() {
            return ReadOnlyIntWrapper.this.floatValue();
        }

        @Override
        public double doubleValue() {
            return ReadOnlyIntWrapper.this.doubleValue();
        }

        @Override
        public Integer getValue() {
            return ReadOnlyIntWrapper.this.getValue();
        }

        @Override
        public void addListener(InvalidationListener listener) {
            ReadOnlyIntWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            ReadOnlyIntWrapper.this.removeListener(listener);
        }

        @Override
        public void addListener(ChangeListener<? super Integer> listener) {
            ReadOnlyIntWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(ChangeListener<? super Integer> listener) {
            ReadOnlyIntWrapper.this.removeListener(listener);
        }

        @Override
        public String toString() {
            return "ReadOnlyIntValue{" +
                    "value=" + ReadOnlyIntWrapper.this.get() +
                    '}';
        }
    }
}
