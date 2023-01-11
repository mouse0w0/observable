package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class ReadOnlyStringWrapper extends SimpleStringValue implements WritableValue<String> {

    private ReadOnlyStringValue readOnlyStringValue;

    public ReadOnlyStringWrapper() {
    }

    public ReadOnlyStringWrapper(String value) {
        super(value);
    }

    public ObservableStringValue toReadOnly() {
        if (readOnlyStringValue == null) {
            readOnlyStringValue = new ReadOnlyStringValue();
        }
        return readOnlyStringValue;
    }

    private class ReadOnlyStringValue implements ObservableStringValue {
        @Override
        public String get() {
            return ReadOnlyStringWrapper.this.get();
        }

        @Override
        public String getValue() {
            return ReadOnlyStringWrapper.this.getValue();
        }

        @Override
        public void addListener(InvalidationListener listener) {
            ReadOnlyStringWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            ReadOnlyStringWrapper.this.removeListener(listener);
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
        public String toString() {
            return "ReadOnlyStringValue{" +
                    "value=" + ReadOnlyStringWrapper.this.get() +
                    '}';
        }


    }
}
