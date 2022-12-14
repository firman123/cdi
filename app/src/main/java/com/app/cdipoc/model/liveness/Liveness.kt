package com.app.cdipoc.model.liveness

import com.google.gson.annotations.Expose

data class Liveness(
    @Expose
    var probability: String? = null,

    @Expose
    var status: Boolean? = null
)