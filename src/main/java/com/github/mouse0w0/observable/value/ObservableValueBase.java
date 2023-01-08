package com.github.mouse0w0.observable.value;

import com.github.mouse0w0.observable.InvalidationListener;

public abstract class ObservableValueBase<T> implements ObservableValue<T> {

    private ListenerHelper<T> listenerHelper;

    @Override
    public void addListener(InvalidationListener listener) {
        listenerHelper = ListenerHelper.addListener(this, listenerHelper, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listenerHelper = ListenerHelper.removeListener(listenerHelper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super T> listener) {
        listenerHelper = ListenerHelper.addListener(this, listenerHelper, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        listenerHelper = ListenerHelper.removeListener(listenerHelper, listener);
    }

    protected void notifyChanged() {
        ListenerHelper.fire(listenerHelper);
    }
}
