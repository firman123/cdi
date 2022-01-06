package com.app.cdipoc.model.biometric

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class BiometricRequest(
    @SerializedName("transactionId")
    var transactionId: String? = null,

    @SerializedName("component")
    var component: String? = null,

    @SerializedName("customer_Id")
    var customerId: String? = null,

    @SerializedName("digital_Id")
    var digitalId: String? = null,

    @SerializedName("requestType")
    var requestType: String? = null,

    @SerializedName("NIK")
    var nik: String? = null,

    @SerializedName("device_Id")
    var deviceId: String? = null,

    @SerializedName("app_Version")
    var appVersion: Double? = null,

    @SerializedName("sdk_Version")
    var sdkVersion: Double? = null,

    @SerializedName("faceThreshold")
    var faceThreshold: Double? = null,

    @SerializedName("passiveLiveness")
    var passiveLiveness: Boolean? = false,

    @SerializedName("liveness")
    var liveness: Boolean? = false,

    @SerializedName("localVerification")
    var localVerification: Boolean? = false,

    @SerializedName("isVerifyWithImage")
    var isVerifyWithImage: Boolean? = false,

    @SerializedName("verifyIdCardFaceImage")
    var verifyIdCardFaceImage: Boolean? = false,

    @SerializedName("biometrics")
    var biometrics: ArrayList<Biometric>? = null
)
