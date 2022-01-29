package cps2.emse.fr.envsmalltalk.utils;

@FunctionalInterface
public interface ThrowableSupplier<T> {
    T get() throws Exception;
}