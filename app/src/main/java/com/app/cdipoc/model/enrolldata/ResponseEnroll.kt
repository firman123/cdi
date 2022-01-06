package com.app.cdipoc.model.enrolldata

import com.google.gson.annotations.SerializedName

data class ResponseEnroll(
    @SerializedName("error_message")
    var errorMessage: String? = null,
    @SerializedName("error_code")
    var errorCode: Int? = null
)