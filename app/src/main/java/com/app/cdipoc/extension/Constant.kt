package com.app.cdipoc.extension

object Constant {
//    const val DEFAULT_URL_PASSIVE = "https://api.verihubs.com/v1/"
//    const val DEFAULT_URL_OCR = "https://live.cdi-systems.com/"
//    const val DEFAULT_URL_ENROLL = "https://sandbox.cdi-systems.com:9443/"
//    const val DEFAULT_URL_BIOMETRIC = "https://sandbox.cdi-systems.com:8443/"
//    const val DEFAULT_URL_BIOMETRIC = "https://sandbox.cdi-systems.com:8443/"
//    const val DEFAULT_URL_BIOMETRIC = "https://sandbox.cdi-systems.com:8443/"
    const val BASE_URL_PASSIVE = "passive_liveness_url"
    const val BASE_URL_OCR = "ocr_url"
    const val BASE_URL_ENROLL = "enroll_url"
    const val BASE_URL_BIOMETRIC = "biometric_url"
    const val BASE_URL_DUKCAPIL = "dukcapil_url"
    const val BASE_URL_LOCAL_VERIFY = "local_verify"
    const val BASE_URL_DEMOGRAPHY = "demography"

    const val APP_ID_VALUE = "adf4f15e-6bb1-45c0-b7a2-a1e554ba7072 "
    const val API_KEY_VALUE = "a4TltvMscWKXDhEp8X5Pv4InmNYuHc32"
    const val API_KEY_OCR = "4b60c66971fc49885c958ae866d25bca445aec265b75e7f2dd9bb91056c2499f"

    interface HEADER {
        companion object {
            const val ACCEPT = "Accept: application/json"
            const val CONTENT_TYPE = "Content-Type: application/json"
            const val CONTENT_TYPE_MULTIPART = "Content-Type: multipart/form-data "
            const val APP_ID = "App-ID"
            const val API_KEY = "API-Key"
        }
    }

    interface PREFERENCE {
        companion object {
            const val DATA_KTP = "data_ktp"
            const val USER_LOGIN = "user_login"
        }
    }

    interface DUKCAPIL_VERIFY {
        companion object {
            const val URL = "verify_url"
            const val USER_ID = "user_id"
            const val THRESHOLD = "threshold"
            const val PASSWORD = "password"
        }
    }

}