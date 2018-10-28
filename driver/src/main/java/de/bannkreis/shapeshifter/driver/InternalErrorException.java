package de.bannkreis.shapeshifter.driver;

import okhttp3.internal.Internal;

import java.util.function.Supplier;

public class InternalErrorException extends RuntimeException {

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Exception;
    }


    public InternalErrorException(Exception e) {
        super(e);
    }

    public static <X> Supplier<X> handle(ThrowingSupplier<X> supplier) {
        return ()-> {
            try {
                return supplier.get();
            } catch (Exception e) {
                throw new InternalErrorException(e);
            }
        };
    }

}
