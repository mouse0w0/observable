package com.github.mouse0w0.observable;

@FunctionalInterface
public interface InvalidationListener {

    void invalidated(Observable observable);
}
