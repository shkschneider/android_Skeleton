package me.shkschneider.app.model;

import me.shkschneider.skeleton.helper.StringHelper;

public class AndroidSdk {

    public String version;
    public String apiLevel;
    public String revision;

    public boolean valid() {
        return (! StringHelper.nullOrEmpty(version)
                && ! StringHelper.nullOrEmpty(apiLevel)
                && ! StringHelper.nullOrEmpty(revision));
    }

}
