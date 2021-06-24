package com.github.mouse0w0.observable.value;

public class ReadOnlyBooleanWrapper extends SimpleBooleanValue implements ReadOnlyWrapper<Boolean> {

    private ReadOnlyBooleanValue readOnlyBooleanValue;

    public ReadOnlyBooleanWrapper() {
    }

    public ReadOnlyBooleanWrapper(boolean value) {
        super(value);
    }

    @Override
    public ObservableBooleanValue toReadOnly() {
        if (readOnlyBooleanValue == null)
            readOnlyBooleanValue = new ReadOnlyBooleanValue();
        return readOnlyBooleanValue;
    }

    @Override
    public String toString() {
        return "ReadOnlyBooleanWrapper{" +
                "value=" + get() +
                '}';
    }

    private class ReadOnlyBooleanValue implements ObservableBooleanValue {
        @Override
        public boolean get() {
            return ReadOnlyBooleanWrapper.this.get();
        }

        @Override
        public Boolean getValue() {
            return ReadOnlyBooleanWrapper.this.getValue();
        }

        @Override
        public void addListener(ValueChangeListener<? super Boolean> listener) {
            ReadOnlyBooleanWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(ValueChangeListener<? super Boolean> listener) {
            ReadOnlyBooleanWrapper.this.removeListener(listener);
        }

        @Override
        public String toString() {
            return "ReadOnlyBooleanValue{" +
                    "value=" + ReadOnlyBooleanWrapper.this.get() +
                    '}';
        }
    }
}
