package me.shkschneider.skeleton.demo.data

import com.google.gson.annotations.SerializedName

data class ShkMod(
        @SerializedName("api")
        val api: Int? = null,
        @SerializedName("buildId")
        val buildId: String?,
        @SerializedName("revision")
        val revision: String?,
        @SerializedName("securityPatch")
        val securityPatch: String?,
        @SerializedName("ro.mod.name")
        val roModName: String?,
        @SerializedName("ro.mod.version")
        val roModVersion: String?,
        @SerializedName("devices")
        val devices: List<String>?
)
