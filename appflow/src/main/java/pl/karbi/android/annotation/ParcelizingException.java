package pl.karbi.android.annotation;

public final class ParcelizingException extends Exception {

    private static final long serialVersionUID = 1L;

    public ParcelizingException(final String message) {
        super(message);
    }

    public ParcelizingException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
