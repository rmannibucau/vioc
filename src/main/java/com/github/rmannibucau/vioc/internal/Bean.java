package com.github.rmannibucau.vioc.internal;

public interface Bean<T> {
    T create(Container container);
    void destroy(T instance);
}
