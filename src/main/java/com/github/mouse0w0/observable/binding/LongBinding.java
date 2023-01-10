package com.github.mouse0w0.observable.binding;

import com.github.mouse0w0.observable.InvalidationListener;
import com.github.mouse0w0.observable.collection.ObservableCollections;
import com.github.mouse0w0.observable.collection.ObservableList;
import com.github.mouse0w0.observable.value.ChangeListener;
import com.github.mouse0w0.observable.value.ListenerHelper;
import com.github.mouse0w0.observable.value.ObservableLongValue;

public abstract class LongBinding implements Binding<Long>, ObservableLongValue {
    private long value;
    private boolean valid;
    private ListenerHelper<Long> listenerHelper;

    @Override
    public void addListener(InvalidationListener listener) {
        listenerHelper = ListenerHelper.addListener(this, listenerHelper, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listenerHelper = ListenerHelper.removeListener(listenerHelper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super Long> listener) {
        listenerHelper = ListenerHelper.addListener(this, listenerHelper, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Long> listener) {
        listenerHelper = ListenerHelper.removeListener(listenerHelper, listener);
    }

    @Override
    public long get() {
        if (!valid) {
            value = computeValue();
            valid = true;
        }
        return value;
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public void invalidate() {
        if (valid) {
            valid = false;
            ListenerHelper.fire(listenerHelper);
        }
    }

    @Override
    public ObservableList<?> getDependencies() {
        return ObservableCollections.emptyObservableList();
    }

    @Override
    public void dispose() {
    }

    protected abstract long computeValue();
}
