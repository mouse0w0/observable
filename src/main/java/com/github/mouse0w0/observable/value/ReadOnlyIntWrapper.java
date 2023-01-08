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
        public byte getByte() {
            return ReadOnlyIntWrapper.this.getByte();
        }

        @Override
        public short getShort() {
            return ReadOnlyIntWrapper.this.getShort();
        }

        @Override
        public int getInt() {
            return ReadOnlyIntWrapper.this.getInt();
        }

        @Override
        public long getLong() {
            return ReadOnlyIntWrapper.this.getLong();
        }

        @Override
        public float getFloat() {
            return ReadOnlyIntWrapper.this.getFloat();
        }

        @Override
        public double getDouble() {
            return ReadOnlyIntWrapper.this.getDouble();
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
