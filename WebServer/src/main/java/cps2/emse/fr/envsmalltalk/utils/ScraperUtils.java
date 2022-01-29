package cps2.emse.fr.envsmalltalk.utils;

import java.util.OptionalDouble;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ScraperUtils {

    public static int interval(int value, int min, int max, String name) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(name + " should be between " + min + " and " + max + ": " + value);
        }
        return value;
    }

    public static int intervalMin(int value, int min, String name) {
        if (value < min) {
            throw new IllegalArgumentException(name + " should be greater than " + min + ": " + value);
        }
        return value;
    }

    public static int intervalMax(int value, int max, String name) {
        if (value > max) {
            throw new IllegalArgumentException(name + " should be lower than " + max + ": " + value);
        }
        return value;
    }

    public static OptionalDouble optionalDoubleOf(String value) {
        if (value.isEmpty()) {
            return OptionalDouble.empty();
        }
        try {
            return OptionalDouble.of(Double.parseDouble(value));
        } catch (Exception e) {
            return OptionalDouble.empty();
        }
    }

    public static <T> T errorIfException(ThrowableSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
