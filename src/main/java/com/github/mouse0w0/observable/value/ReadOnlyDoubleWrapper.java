package com.github.mouse0w0.observable.value;

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
        public byte getByte() {
            return ReadOnlyDoubleWrapper.this.getByte();
        }

        @Override
        public short getShort() {
            return ReadOnlyDoubleWrapper.this.getShort();
        }

        @Override
        public int getInt() {
            return ReadOnlyDoubleWrapper.this.getInt();
        }

        @Override
        public long getLong() {
            return ReadOnlyDoubleWrapper.this.getLong();
        }

        @Override
        public float getFloat() {
            return ReadOnlyDoubleWrapper.this.getFloat();
        }

        @Override
        public double getDouble() {
            return ReadOnlyDoubleWrapper.this.getDouble();
        }

        @Override
        public Double getValue() {
            return ReadOnlyDoubleWrapper.this.getValue();
        }

        @Override
        public void addListener(ValueChangeListener<? super Double> listener) {
            ReadOnlyDoubleWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(ValueChangeListener<? super Double> listener) {
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

