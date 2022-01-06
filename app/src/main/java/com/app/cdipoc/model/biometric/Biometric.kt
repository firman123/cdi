package com.app.cdipoc.model.biometric

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Biometric (
    @SerializedName("position")
    @Expose
    var position: String? = null,

    @SerializedName("image")
    @Expose
    var image: String? = null,

    @SerializedName("template")
    var template: String? = null,

    @SerializedName("type")
    @Expose
    var type: String? = null
)
