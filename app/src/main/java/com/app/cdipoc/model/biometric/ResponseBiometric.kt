package com.app.cdipoc.model.biometric

import com.google.gson.annotations.SerializedName

class ResponseBiometric(
    var verificationResult: Boolean? = false,
    var component: String? = null,
    var verificationScore: Double? = -0.0,
    var errorMessage: String? = null,
    var errorCode: Int? = null,
    var transactionId: String? = null
)
