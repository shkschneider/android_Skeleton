package me.shkschneider.skeleton.demo.data

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

class ShkMod {

    @SerializedName("api")
    var api: Int? = null
    @SerializedName("buildId")
    var buildId: String? = null
    @SerializedName("revision")
    var revision: String? = null
    @SerializedName("securityPatch")
    var securityPatch: String? = null
    @SerializedName("ro.mod.name")
    var roModName: String? = null
    @SerializedName("ro.mod.verison")
    var roModVersion: String? = null
    @SerializedName("devices")
    var devices: JsonArray? = null

}
