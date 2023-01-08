package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class ReadOnlyLongWrapper extends SimpleLongValue implements ReadOnlyWrapper<Long> {

    private ReadOnlyLongValue readOnlyLongValue;

    public ReadOnlyLongWrapper() {
    }

    public ReadOnlyLongWrapper(long value) {
        super(value);
    }

    @Override
    public ObservableLongValue toReadOnly() {
        if (readOnlyLongValue == null)
            readOnlyLongValue = new ReadOnlyLongValue();
        return readOnlyLongValue;
    }

    @Override
    public String toString() {
        return "ReadOnlyLongWrapper{" +
                "value=" + get() +
                '}';
    }

    private class ReadOnlyLongValue implements ObservableLongValue {
        @Override
        public long get() {
            return ReadOnlyLongWrapper.this.get();
        }

        @Override
        public byte getByte() {
            return ReadOnlyLongWrapper.this.getByte();
        }

        @Override
        public short getShort() {
            return ReadOnlyLongWrapper.this.getShort();
        }

        @Override
        public int getInt() {
            return ReadOnlyLongWrapper.this.getInt();
        }

        @Override
        public long getLong() {
            return ReadOnlyLongWrapper.this.getLong();
        }

        @Override
        public float getFloat() {
            return ReadOnlyLongWrapper.this.getFloat();
        }

        @Override
        public double getDouble() {
            return ReadOnlyLongWrapper.this.getDouble();
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
