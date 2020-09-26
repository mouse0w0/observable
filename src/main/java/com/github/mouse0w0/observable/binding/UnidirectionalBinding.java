package com.github.mouse0w0.observable.binding;

import com.github.mouse0w0.observable.value.MutableValue;
import com.github.mouse0w0.observable.value.ObservableValue;

import java.lang.ref.WeakReference;

public abstract class UnidirectionalBinding<T> extends AbstractBinding<T> {

    public static <T> UnidirectionalBinding<T> bind(MutableValue<T> source, ObservableValue<T> target) {
        checkParameters(source, target);
        UnidirectionalBinding<T> binding = new ObjectBinding<>(source, target);
        source.setValue(target.getValue());
        target.addChangeListener(binding);
        return binding;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> void unbind(MutableValue<T> source, ObservableValue<T> target) {
        checkParameters(source, target);
        AbstractBinding forRemoval = createForRemovalBinding(source, target);
        target.removeChangeListener(forRemoval);
    }

    private static void checkParameters(Object source, Object target) {
        if (source == null || target == null) {
            throw new NullPointerException();
        }
        if (source == target) {
            throw new IllegalArgumentException("Cannot bind mutable value to itself");
        }
    }

    private UnidirectionalBinding(Object source, Object target) {
        super(source, target);
    }

    private static class ObjectBinding<T> extends UnidirectionalBinding<T> {

        private final WeakReference<MutableValue<T>> source;
        private final WeakReference<ObservableValue<T>> target;
        private boolean updating = false;

        private ObjectBinding(MutableValue<T> source, ObservableValue<T> target) {
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
                ObservableValue<T> target = this.target.get();
                if (source == null) {
                    if (target != null) {
                        target.removeChangeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        source.setValue(newValue);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }
}
