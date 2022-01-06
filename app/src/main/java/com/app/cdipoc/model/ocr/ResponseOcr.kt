package com.app.cdipoc.model.ocr

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseOcr(
    var demographics: Demographics? = null,
    var transactionSource: String? = null,
    var errorMessage: String? = null,
    var errorCode: Int? = null,
    var transactionId: String? = null,
): Parcelable