package com.github.mouse0w0.observable.binding;

import com.github.mouse0w0.observable.WeakListener;
import com.github.mouse0w0.observable.value.MutableValue;
import com.github.mouse0w0.observable.value.ObservableValue;
import com.github.mouse0w0.observable.value.ValueChangeListener;

import java.lang.ref.WeakReference;

public abstract class BidirectionalBinding<T> implements ValueChangeListener<T>, WeakListener {

    public static <T> BidirectionalBinding<T> bind(MutableValue<T> mutable1, MutableValue<T> mutable2) {
        checkParameters(mutable1, mutable2);
        BidirectionalBinding<T> binding = new BidirectionalObjectBinding<>(mutable1, mutable2);
        mutable1.setValue(mutable2.getValue());
        mutable1.addChangeListener(binding);
        mutable2.addChangeListener(binding);
        return binding;
    }

    public static <T> void unbind(MutableValue<T> mutable1, MutableValue<T> mutable2) {
        checkParameters(mutable1, mutable2);
        BidirectionalBinding binding = new ForRemovalBinding(mutable1, mutable2);
        mutable1.removeChangeListener(binding);
        mutable2.removeChangeListener(binding);
    }

    private static void checkParameters(Object mutable1, Object mutable2) {
        if (mutable1 == null || mutable2 == null) {
            throw new NullPointerException();
        }
        if (mutable1 == mutable2) {
            throw new IllegalArgumentException("Cannot bind mutable value to itself");
        }
    }

    private final int hash;

    private BidirectionalBinding(Object mutable1, Object mutable2) {
        hash = mutable1.hashCode() * 31 + mutable2.hashCode();
    }

    protected abstract Object getMutable1();

    protected abstract Object getMutable2();

    @Override
    public boolean wasGarbageCollected() {
        return getMutable1() == null || getMutable2() == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        Object observable1 = getMutable1();
        Object observable2 = getMutable2();
        if (observable1 == null || observable2 == null) return false;

        if (o == null || getClass() != o.getClass()) return false;
        BidirectionalBinding<?> that = (BidirectionalBinding<?>) o;

        Object thatObservable1 = that.getMutable1();
        Object thatObservable2 = that.getMutable2();
        if (thatObservable1 == null || thatObservable2 == null) return false;

        return (observable1 == thatObservable1 && observable2 == thatObservable2) ||
                (observable1 == thatObservable2 && observable2 == thatObservable1);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    private static class BidirectionalObjectBinding<T> extends BidirectionalBinding<T> {

        private final WeakReference<MutableValue<T>> mutable1;
        private final WeakReference<MutableValue<T>> mutable2;
        private boolean updating = false;

        private BidirectionalObjectBinding(MutableValue<T> mutable1, MutableValue<T> mutable2) {
            super(mutable1, mutable2);
            this.mutable1 = new WeakReference<>(mutable1);
            this.mutable2 = new WeakReference<>(mutable2);
        }

        @Override
        protected Object getMutable1() {
            return mutable1.get();
        }

        @Override
        protected Object getMutable2() {
            return mutable2.get();
        }

        @Override
        public void onChanged(ObservableValue<? extends T> observable, T oldValue, T newValue) {
            if (!updating) {
                MutableValue<T> mutable1 = this.mutable1.get();
                MutableValue<T> mutable2 = this.mutable2.get();
                if (mutable1 == null || mutable2 == null) {
                    if (mutable1 != null) {
                        mutable1.removeChangeListener(this);
                    }
                    if (mutable2 != null) {
                        mutable2.removeChangeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (mutable1 == observable) {
                            mutable2.setValue(newValue);
                        } else {
                            mutable1.setValue(newValue);
                        }
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class ForRemovalBinding extends BidirectionalBinding<Object> {

        private final Object mutable1;
        private final Object mutable2;

        private ForRemovalBinding(Object mutable1, Object mutable2) {
            super(mutable1, mutable2);
            this.mutable1 = mutable1;
            this.mutable2 = mutable2;
        }

        @Override
        protected Object getMutable1() {
            return mutable1;
        }

        @Override
        protected Object getMutable2() {
            return mutable2;
        }

        @Override
        public void onChanged(ObservableValue<?> observable, Object oldValue, Object newValue) {
            throw new RuntimeException("Should not reach here");
        }
    }
}
