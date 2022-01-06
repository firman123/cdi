package com.app.cdipoc.model.enrolldata

import com.google.gson.annotations.SerializedName

data class RequestEnroll(
    @SerializedName("NIK")
    var nik: String? = null,

    @SerializedName("face_image")
    var faceImage: String? = null
)