package me.shkschneider.skeleton;

@SuppressWarnings("unused")
public class Twitter {

    protected static Twitter INSTANCE = null;

    public static interface TwitterCallback {

        public void twitterCallback(final String token);

    }

}
