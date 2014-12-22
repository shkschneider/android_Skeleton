package me.shkschneider.skeleton;

public class SkeletonException extends Exception {

    public SkeletonException() {
        // Empty
    }

    public SkeletonException(final String message) {
        super(message);
    }

    public SkeletonException(final Throwable throwable) {
        this(throwable.getClass().getName() + ": " + throwable.getMessage());
    }

}
