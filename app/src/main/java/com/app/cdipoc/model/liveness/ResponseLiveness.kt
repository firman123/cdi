package com.app.cdipoc.model.liveness

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ResponseLiveness(
    @SerializedName("bounding_box")
    var boundingBox: BoundingBox? = null,

    @Expose
    var code: Long? = null,

    @SerializedName("face_landmark")
    var faceLandmark: FaceLandmark? = null,

    @Expose
    var liveness: Liveness? = null,

    @Expose
    var message: String? = null,

    @Expose
    var rotation: Long? = null,

    @SerializedName("session_id")
    var sessionId: String? = null,

    @Expose
    var status: Int? = null,

    @Expose
    var timestamp: Double? = null,
)