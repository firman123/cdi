package com.app.cdipoc.model.biometric

import com.google.gson.annotations.SerializedName

class ResponseBiometric(
    var verificationResult: String? = null,
    var component: String? = null,
    var verificationScore: String? = null,
    var errorMessage: String? = null,
    var errorCode: Int? = null,
    var transactionId: String? = null
)
