package com.github.mouse0w0.observable.value;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface ObservableValue<T> {

    T getValue();

    void addListener(ValueChangeListener<? super T> listener);

    void removeListener(ValueChangeListener<? super T> listener);

    default Optional<T> optional() {
        return Optional.ofNullable(getValue());
    }

    default boolean isPresent() {
        return getValue() != null;
    }

    default boolean isEmpty() {
        return getValue() == null;
    }

    default void ifPresent(Consumer<? super T> action) {
        final T value = getValue();
        if (value != null) {
            action.accept(value);
        }
    }

    default void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
        final T value = getValue();
        if (value != null) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
    }

    default Optional<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        final T value = getValue();
        if (value == null)
            return Optional.empty();
        else
            return predicate.test(value) ? Optional.of(value) : Optional.empty();
    }

    default <U> Optional<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        final T value = getValue();
        if (value == null)
            return Optional.empty();
        else {
            return Optional.ofNullable(mapper.apply(value));
        }
    }

    default <U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
        Objects.requireNonNull(mapper);
        final T value = getValue();
        if (value == null)
            return Optional.empty();
        else {
            return Objects.requireNonNull(mapper.apply(value));
        }
    }

    default T orElse(T other) {
        final T value = getValue();
        return value != null ? value : other;
    }

    default T orElseGet(Supplier<? extends T> supplier) {
        final T value = getValue();
        return value != null ? value : supplier.get();
    }

    default T orElseThrow() {
        final T value = getValue();
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    default <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        final T value = getValue();
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    default Stream<T> stream() {
        final T value = getValue();
        if (value == null) {
            return Stream.empty();
        } else {
            return Stream.of(value);
        }
    }
}
