package com.github.mouse0w0.observable.binding;

import com.github.mouse0w0.observable.WeakListener;
import com.github.mouse0w0.observable.value.ObservableValue;
import com.github.mouse0w0.observable.value.ValueChangeListener;

abstract class AbstractBinding<T> implements WeakListener, ValueChangeListener<T> {

    public static AbstractBinding<?> createForRemovalBinding(Object source, Object target) {
        return new ForRemovalBinding(source, target);
    }

    protected final int hash;

    public AbstractBinding(Object source, Object target) {
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
        AbstractBinding<?> other = (AbstractBinding<?>) o;

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

    private static class ForRemovalBinding extends AbstractBinding<Object> {

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
