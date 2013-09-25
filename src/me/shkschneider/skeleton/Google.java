package me.shkschneider.skeleton;

@SuppressWarnings("unused")
public class Google {

    protected static Google INSTANCE = null;

    public static interface GoogleCallback {

        public void googleCallback(final String token);

    }

}
