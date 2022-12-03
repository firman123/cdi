package com.app.cdipoc.model.localverify

import com.app.cdipoc.model.dukcapil.Error
import com.google.gson.annotations.SerializedName

class LocalVerifyResponse(
    var verificationResult: Boolean? = false,
    var component: String? = null,
    var verificationScore: String? = null,
    var errorMessage: String? = null,
    var errorCode: Int? = 0,
    var transactionId: String? = null
)