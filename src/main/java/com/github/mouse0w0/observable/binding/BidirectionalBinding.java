package com.github.mouse0w0.observable.binding;

import com.github.mouse0w0.observable.WeakListener;
import com.github.mouse0w0.observable.value.ChangeListener;
import com.github.mouse0w0.observable.value.MutableValue;
import com.github.mouse0w0.observable.value.ObservableValue;

import java.lang.ref.WeakReference;

public abstract class BidirectionalBinding<T> implements ChangeListener<T>, WeakListener {

    public static <T> BidirectionalBinding<T> bind(MutableValue<T> source, MutableValue<T> target) {
        checkParameters(source, target);
        BidirectionalBinding<T> binding = new ObjectBinding<>(source, target);
        source.setValue(target.getValue());
        source.addListener(binding);
        target.addListener(binding);
        return binding;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> void unbind(MutableValue<T> source, MutableValue<T> target) {
        checkParameters(source, target);
        ForRemovalBinding forRemoval = new ForRemovalBinding(source, target);
        source.removeListener(forRemoval);
        target.removeListener(forRemoval);
    }

    private static void checkParameters(Object source, Object target) {
        if (source == null || target == null) {
            throw new NullPointerException();
        }
        if (source == target) {
            throw new IllegalArgumentException("Cannot bind mutable value to itself");
        }
    }

    protected final int hash;

    public BidirectionalBinding(Object source, Object target) {
        hash = source.hashCode() * 31 + target.hashCode();
    }

    protected abstract Object getSource();

    protected abstract Object getTarget();

    @Override
    public boolean wasGarbageCollected() {
        return getSource() == null || getTarget() == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        Object source = getSource();
        Object target = getTarget();
        if (source == null || target == null) return false;

        if (o == null || getClass() != o.getClass()) return false;
        BidirectionalBinding<?> other = (BidirectionalBinding<?>) o;

        Object otherSource = other.getSource();
        Object otherTarget = other.getTarget();
        if (otherSource == null || otherTarget == null) return false;

        return (source == otherSource && target == otherTarget) ||
                (source == otherTarget && target == otherSource);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    private static class ObjectBinding<T> extends BidirectionalBinding<T> {

        private final WeakReference<MutableValue<T>> source;
        private final WeakReference<MutableValue<T>> target;
        private boolean updating = false;

        private ObjectBinding(MutableValue<T> source, MutableValue<T> target) {
            super(source, target);
            this.source = new WeakReference<>(source);
            this.target = new WeakReference<>(target);
        }

        @Override
        protected Object getSource() {
            return source.get();
        }

        @Override
        protected Object getTarget() {
            return target.get();
        }

        @Override
        public void onChanged(ObservableValue<? extends T> observable, T oldValue, T newValue) {
            if (!updating) {
                MutableValue<T> source = this.source.get();
                MutableValue<T> target = this.target.get();
                if (source == null || target == null) {
                    if (source != null) {
                        source.removeListener(this);
                    }
                    if (target != null) {
                        target.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (source == observable) {
                            target.setValue(newValue);
                        } else {
                            source.setValue(newValue);
                        }
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class ForRemovalBinding extends BidirectionalBinding<Object> {

        private final Object source;
        private final Object target;

        private ForRemovalBinding(Object source, Object target) {
            super(source, target);
            this.source = source;
            this.target = target;
        }

        @Override
        protected Object getSource() {
            return source;
        }

        @Override
        protected Object getTarget() {
            return target;
        }

        @Override
        public void onChanged(ObservableValue<?> observable, Object oldValue, Object newValue) {
            throw new UnsupportedOperationException("Should not reach here");
        }
    }
}
