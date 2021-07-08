package com.github.mouse0w0.observable.value;

public class ReadOnlyFloatWrapper extends SimpleFloatValue implements ReadOnlyWrapper<Float> {

    private ReadOnlyFloatValue readOnlyFloatValue;

    public ReadOnlyFloatWrapper() {
    }

    public ReadOnlyFloatWrapper(float value) {
        super(value);
    }

    @Override
    public ObservableFloatValue toReadOnly() {
        if (readOnlyFloatValue == null)
            readOnlyFloatValue = new ReadOnlyFloatValue();
        return readOnlyFloatValue;
    }

    @Override
    public String toString() {
        return "ReadOnlyFloatWrapper{" +
                "value=" + get() +
                '}';
    }

    private class ReadOnlyFloatValue implements ObservableFloatValue {
        @Override
        public float get() {
            return ReadOnlyFloatWrapper.this.get();
        }

        @Override
        public byte getByte() {
            return ReadOnlyFloatWrapper.this.getByte();
        }

        @Override
        public short getShort() {
            return ReadOnlyFloatWrapper.this.getShort();
        }

        @Override
        public int getInt() {
            return ReadOnlyFloatWrapper.this.getInt();
        }

        @Override
        public long getLong() {
            return ReadOnlyFloatWrapper.this.getLong();
        }

        @Override
        public float getFloat() {
            return ReadOnlyFloatWrapper.this.getFloat();
        }

        @Override
        public double getDouble() {
            return ReadOnlyFloatWrapper.this.getDouble();
        }

        @Override
        public Float getValue() {
            return ReadOnlyFloatWrapper.this.getValue();
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
