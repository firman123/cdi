package com.app.cdipoc.model.localverify
import com.google.gson.annotations.SerializedName


data class LocalVerifyRequest(
    @SerializedName("app_Version")
    val appVersion: String,
    val biometrics: List<Biometric>,
    val component: String,
    @SerializedName("customer_Id")
    val customerId: String,
    @SerializedName("device_Id")
    val deviceId: String,
    @SerializedName("digital_Id")
    val digitalId: String,
    val faceThreshold: String,
    val isVerifyWithImage: Boolean,
    val liveness: Boolean,
    val localVerification: Boolean,
    @SerializedName("NIK")
    val nIK: String,
    val passiveLiveness: String,
    val requestType: String,
    @SerializedName("sdk_Version")
    val sdkVersion: String,
    val transactionId: String,
    val verifyIdCardFaceImage: Boolean
)

data class Biometric(
    val image: String,
    val position: String,
    val template: String? = null,
    val type: String
)