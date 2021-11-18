package com.github.mouse0w0.observable.value;

public abstract class ObservableValueBase<T> implements ObservableValue<T> {

    private ListenerHelper<T> listenerHelper;

    @Override
    public void addListener(ChangeListener<? super T> listener) {
        listenerHelper = ListenerHelper.addListener(listenerHelper, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        listenerHelper = ListenerHelper.removeListener(listenerHelper, listener);
    }

    protected void notifyChanged(T oldValue, T newValue) {
        ListenerHelper.fire(listenerHelper, this, oldValue, newValue);
    }
}
