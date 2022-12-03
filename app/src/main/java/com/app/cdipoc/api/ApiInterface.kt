package com.app.cdipoc.api

import com.app.cdipoc.extension.Constant
import com.app.cdipoc.model.biometric.BiometricRequest
import com.app.cdipoc.model.biometric.ResponseBiometric
import com.app.cdipoc.model.demography.DemographyRequest
import com.app.cdipoc.model.demography.DemographyResponse
import com.app.cdipoc.model.dukcapil.DukcapilRequest
import com.app.cdipoc.model.dukcapil.DukcapilResponse
import com.app.cdipoc.model.enrolldata.RequestEnroll
import com.app.cdipoc.model.enrolldata.ResponseEnroll
import com.app.cdipoc.model.liveness.ResponseLiveness
import com.app.cdipoc.model.localverify.LocalVerifyRequest
import com.app.cdipoc.model.localverify.LocalVerifyResponse
import com.app.cdipoc.model.ocr.ResponseOcr
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @Headers(Constant.HEADER.ACCEPT, Constant.HEADER.CONTENT_TYPE)
    @POST("face/liveness")
    fun faceLiveness(@HeaderMap headerMap: Map<String, String>, @Body body: Map<String, String>): Call<ResponseLiveness>

    @Headers(Constant.HEADER.ACCEPT, Constant.HEADER.CONTENT_TYPE)
    @POST("eKYC_MW/ocr?")
    fun cdiOCR(@Body body: Map<String, String>): Call<ResponseOcr>

    @Headers(Constant.HEADER.ACCEPT, Constant.HEADER.CONTENT_TYPE)
    @POST("eKYC_MW/request")
    fun biometric(@Body body: BiometricRequest): Call<ResponseBiometric>

    @Headers(Constant.HEADER.ACCEPT, Constant.HEADER.CONTENT_TYPE)
    @POST("ReTemplate_MW/request")
    fun enRoll(@Body body: RequestEnroll): Call<ResponseEnroll>

    @Headers(Constant.HEADER.ACCEPT, Constant.HEADER.CONTENT_TYPE)
    @POST("eKYC_MW/mohaverifyid")
    fun dukcapil(@Body body: DukcapilRequest): Call<DukcapilResponse>

    @Headers(Constant.HEADER.ACCEPT, Constant.HEADER.CONTENT_TYPE)
    @POST("eKYC_MW/request")
    fun localVerify(@Body body: LocalVerifyRequest): Call<LocalVerifyResponse>

    @Headers(Constant.HEADER.ACCEPT, Constant.HEADER.CONTENT_TYPE)
    @POST("POC_eKYC_MW/verifyDemographics?")
    fun demography(@Body body: DemographyRequest): Call<DemographyResponse>

}