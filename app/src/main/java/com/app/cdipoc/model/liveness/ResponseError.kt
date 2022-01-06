package com.app.cdipoc.model.liveness

import com.google.gson.annotations.Expose

data class ResponseError(
    @Expose
    var status: Long? = null,
    var message: String? = null
)