package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public class ReadOnlyObjectWrapper<T> extends SimpleObjectValue<T> implements WritableValue<T> {

    private ReadOnlyObjectValue readOnlyObjectValue;

    public ReadOnlyObjectWrapper() {
    }

    public ReadOnlyObjectWrapper(T value) {
        super(value);
    }

    public ObservableObjectValue<T> toReadOnly() {
        if (readOnlyObjectValue == null) {
            readOnlyObjectValue = new ReadOnlyObjectValue();
        }
        return readOnlyObjectValue;
    }

    private class ReadOnlyObjectValue implements ObservableObjectValue<T> {
        @Override
        public T get() {
            return ReadOnlyObjectWrapper.this.get();
        }

        @Override
        public T getValue() {
            return ReadOnlyObjectWrapper.this.getValue();
        }

        @Override
        public void addListener(InvalidationListener listener) {
            ReadOnlyObjectWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            ReadOnlyObjectWrapper.this.removeListener(listener);
        }

        @Override
        public void addListener(ChangeListener<? super T> listener) {
            ReadOnlyObjectWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(ChangeListener<? super T> listener) {
            ReadOnlyObjectWrapper.this.removeListener(listener);
        }

        @Override
        public String toString() {
            return "ReadOnlyObjectValue{" +
                    "value=" + ReadOnlyObjectWrapper.this.get() +
                    '}';
        }


    }
}
