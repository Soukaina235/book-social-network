package com.soukaina.book.exception;

// extends RuntimeException and not Throwable
// because we want it to be an unchecked exception and it will only occur at runtime
public class OperationNotPermittedException extends RuntimeException {
    public OperationNotPermittedException(String msg) {
        super(msg);
    }
}
