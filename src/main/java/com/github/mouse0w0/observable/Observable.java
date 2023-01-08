package com.github.mouse0w0.observable;

public interface Observable {

    void addListener(InvalidationListener listener);

    void removeListener(InvalidationListener listener);
}
