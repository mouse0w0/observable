package com.github.mouse0w0.observable.value;

public class ReadOnlyStringWrapper extends SimpleStringValue implements ReadOnlyWrapper<String> {

    private ReadOnlyStringValue readOnlyStringValue;

    public ReadOnlyStringWrapper() {
    }

    public ReadOnlyStringWrapper(String value) {
        super(value);
    }

    @Override
    public ObservableStringValue toReadOnly() {
        if (readOnlyStringValue == null) {
            readOnlyStringValue = new ReadOnlyStringValue();
        }
        return readOnlyStringValue;
    }

    @Override
    public String toString() {
        return "ReadOnlyStringWrapper{" +
                "value=" + get() +
                '}';
    }

    private class ReadOnlyStringValue implements ObservableStringValue {
        @Override
        public String getValue() {
            return ReadOnlyStringWrapper.this.getValue();
        }

        @Override
        public void addListener(ChangeListener<? super String> listener) {
            ReadOnlyStringWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(ChangeListener<? super String> listener) {
            ReadOnlyStringWrapper.this.removeListener(listener);
        }

        @Override
        public String get() {
            return getValue();
        }

        @Override
        public String toString() {
            return "ReadOnlyStringValue{" +
                    "value=" + ReadOnlyStringWrapper.this.get() +
                    '}';
        }
    }
}
