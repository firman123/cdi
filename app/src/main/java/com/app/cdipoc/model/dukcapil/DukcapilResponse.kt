package com.app.cdipoc.model.dukcapil
import com.google.gson.annotations.SerializedName


data class DukcapilResponse(
    var error: Error?  = null,
    var httpResponseCode: String? = null,
    var matchScore: String? = null,
    var quotaLimiter: String? = null,
    var transactionId: String?  = null,
    var uid: String? = null,
    var verificationResult: Boolean? = false
)

data class Error(
    val errorCode: Int?,
    var errorMessage: String?
)