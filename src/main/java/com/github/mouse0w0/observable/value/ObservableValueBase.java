package com.github.mouse0w0.observable.value;

public abstract class ObservableValueBase<T> implements ObservableValue<T> {

    private ListenerHelper<T> helper;

    @Override
    public void addListener(ChangeListener<? super T> listener) {
        helper = ListenerHelper.addListener(helper, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        helper = ListenerHelper.removeListener(helper, listener);
    }

    protected void fireValueChangedEvent(T oldValue, T newValue) {
        ListenerHelper.fireValueChangeEvent(helper, this, oldValue, newValue);
    }
}
