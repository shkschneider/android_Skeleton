package me.shkschneider.skeleton.network;

public class WebServiceException extends Exception {

    public static final int INTERNAL_ERROR = 666;

    private int mCode;

    public WebServiceException(final int responseCode, final String responseMessage) {
        super(responseMessage);
        mCode = responseCode;
    }

    public int getCode() {
        return mCode;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return String.format("%d %s", getCode(), getMessage());
    }

}
