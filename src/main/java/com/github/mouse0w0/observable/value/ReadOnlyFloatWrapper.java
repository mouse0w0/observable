package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class ReadOnlyFloatWrapper extends SimpleFloatValue implements WritableValue<Float> {

    private ReadOnlyFloatValue readOnlyFloatValue;

    public ReadOnlyFloatWrapper() {
    }

    public ReadOnlyFloatWrapper(float value) {
        super(value);
    }

    public ObservableFloatValue toReadOnly() {
        if (readOnlyFloatValue == null)
            readOnlyFloatValue = new ReadOnlyFloatValue();
        return readOnlyFloatValue;
    }

    private class ReadOnlyFloatValue implements ObservableFloatValue {
        @Override
        public float get() {
            return ReadOnlyFloatWrapper.this.get();
        }

        @Override
        public int intValue() {
            return ReadOnlyFloatWrapper.this.intValue();
        }

        @Override
        public long longValue() {
            return ReadOnlyFloatWrapper.this.longValue();
        }

        @Override
        public float floatValue() {
            return ReadOnlyFloatWrapper.this.floatValue();
        }

        @Override
        public double doubleValue() {
            return ReadOnlyFloatWrapper.this.doubleValue();
        }

        @Override
        public Float getValue() {
            return ReadOnlyFloatWrapper.this.getValue();
        }

        @Override
        public void addListener(InvalidationListener listener) {
            ReadOnlyFloatWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            ReadOnlyFloatWrapper.this.removeListener(listener);
        }

        @Override
        public void addListener(ChangeListener<? super Float> listener) {
            ReadOnlyFloatWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(ChangeListener<? super Float> listener) {
            ReadOnlyFloatWrapper.this.removeListener(listener);
        }

        @Override
        public String toString() {
            return "ReadOnlyFloatValue{" +
                    "value=" + ReadOnlyFloatWrapper.this.get() +
                    '}';
        }
    }
}
