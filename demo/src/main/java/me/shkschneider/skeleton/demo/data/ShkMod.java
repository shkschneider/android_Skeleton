package me.shkschneider.skeleton.demo.data;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

public class ShkMod {

    @SerializedName("api")
    public Integer api;
    @SerializedName("buildId")
    public String buildId;
    @SerializedName("revision")
    public String revision;
    @SerializedName("securityPatch")
    public String securityPatch;
    @SerializedName("ro.mod.name")
    public String roModName;
    @SerializedName("ro.mod.verison")
    public String roModVersion;
    @SerializedName("devices")
    public JsonArray devices;

}
