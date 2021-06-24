package com.github.mouse0w0.observable.value;

public class ReadOnlyObjectWrapper<T> extends SimpleObjectValue<T> implements ReadOnlyWrapper<T> {

    private ReadOnlyObjectValue readOnlyObjectValue;

    public ReadOnlyObjectWrapper() {
    }

    public ReadOnlyObjectWrapper(T value) {
        super(value);
    }

    @Override
    public ObservableObjectValue<T> toReadOnly() {
        if (readOnlyObjectValue == null) {
            readOnlyObjectValue = new ReadOnlyObjectValue();
        }
        return readOnlyObjectValue;
    }

    @Override
    public String toString() {
        return "ReadOnlyObjectWrapper{" +
                "value=" + get() +
                '}';
    }

    private class ReadOnlyObjectValue implements ObservableObjectValue<T> {
        @Override
        public T getValue() {
            return ReadOnlyObjectWrapper.this.getValue();
        }

        @Override
        public void addListener(ValueChangeListener<? super T> listener) {
            ReadOnlyObjectWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(ValueChangeListener<? super T> listener) {
            ReadOnlyObjectWrapper.this.removeListener(listener);
        }

        @Override
        public T get() {
            return getValue();
        }

        @Override
        public String toString() {
            return "ReadOnlyObjectValue{" +
                    "value=" + ReadOnlyObjectWrapper.this.get() +
                    '}';
        }
    }
}
