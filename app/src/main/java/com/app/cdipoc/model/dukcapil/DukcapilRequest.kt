package com.app.cdipoc.model.dukcapil

data class DukcapilRequest(
    val NIK: String,
    val customer_Id: String,
    val faceImage: String,
    val faceThreshold: Int,
    val ip: String,
    val password: String,
    val thresholdInPercentage: Boolean,
    val transactionId: String,
    val transactionSource: String,
    val user_id: String
)