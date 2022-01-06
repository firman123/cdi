package com.app.cdipoc.ui.camera

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.cdipoc.api.ApiClient
import com.app.cdipoc.api.ApiHeader
import com.app.cdipoc.extension.Constant
import com.app.cdipoc.extension.Utils
import com.app.cdipoc.model.biometric.BiometricRequest
import com.app.cdipoc.model.biometric.ResponseBiometric
import com.app.cdipoc.model.enrolldata.RequestEnroll
import com.app.cdipoc.model.enrolldata.ResponseEnroll
import com.app.cdipoc.model.liveness.ResponseLiveness
import com.app.cdipoc.model.ocr.ResponseOcr
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraViewModel : ViewModel() {

    fun cdiOcr(context: Context, body: HashMap<String, String>): MutableLiveData<ResponseOcr> {
        val result = MutableLiveData<ResponseOcr>()
        ApiClient.getClient(Constant.BASE_URL_OCR).cdiOCR(body)
            .enqueue(object : Callback<ResponseOcr> {
                override fun onResponse(call: Call<ResponseOcr>, response: Response<ResponseOcr>) {
                    when (response.code()) {
                        200 -> {
                            Log.d("CAMERA ", "onResponse_DATA : 200 ")
                            result.postValue(response.body())
                        }
                        else -> {
                            val gson = Gson()
                            val type = object : TypeToken<ResponseOcr>() {}.type
                            val errorResponse: ResponseOcr? =
                                gson.fromJson(response.errorBody()!!.charStream(), type)
                            result.value = errorResponse
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseOcr>, t: Throwable) {
                    val res = ResponseOcr(null)
                    res.errorMessage = Utils.failureMessage(context, t)
                    result.value = res
                }
            })
        return result
    }

    fun liveness(
        context: Context,
        body: HashMap<String, String>
    ): MutableLiveData<ResponseLiveness> {
        val result = MutableLiveData<ResponseLiveness>()
        ApiClient.getClient(Constant.BASE_URL_PASSIVE).faceLiveness(ApiHeader.headerClient(context), body)
            .enqueue(object : Callback<ResponseLiveness> {
                override fun onResponse(
                    call: Call<ResponseLiveness>,
                    response: Response<ResponseLiveness>
                ) {
                    when (response.code()) {
                        200 -> {
                            result.value = response.body()
                        }

                        else -> {
                            val gson = Gson()
                            val type = object : TypeToken<ResponseLiveness>() {}.type
                            val errorResponse: ResponseLiveness? =
                                gson.fromJson(response.errorBody()!!.charStream(), type)
                            result.value = errorResponse

                        }
                    }
                }

                override fun onFailure(call: Call<ResponseLiveness>, t: Throwable) {
                    val res = ResponseLiveness(null)
                    res.message = Utils.failureMessage(context, t)
                    result.value = res
                }

            })

        return result
    }

    fun enrollData(
        context: Context,
        body: RequestEnroll
    ): MutableLiveData<ResponseEnroll> {
        val result = MutableLiveData<ResponseEnroll>()
        ApiClient.getClient(Constant.BASE_URL_ENROLL).enRoll( body)
            .enqueue(object : Callback<ResponseEnroll> {
                override fun onResponse(
                    call: Call<ResponseEnroll>,
                    response: Response<ResponseEnroll>
                ) {
                    when (response.code()) {
                        200 -> {
                            result.value = response.body()
                        }

                        else -> {
                            val gson = Gson()
                            val type = object : TypeToken<ResponseEnroll>() {}.type
                            val errorResponse: ResponseEnroll? =
                                gson.fromJson(response.errorBody()!!.charStream(), type)
                            result.value = errorResponse

                        }
                    }
                }

                override fun onFailure(call: Call<ResponseEnroll>, t: Throwable) {
                    val res = ResponseEnroll(null)
                    res.errorMessage = Utils.failureMessage(context, t)
                    result.value = res
                }

            })

        return result
    }

    fun biometric(
        context: Context,
        body: BiometricRequest
    ): MutableLiveData<ResponseBiometric> {
        val result = MutableLiveData<ResponseBiometric>()
        ApiClient.getClient(Constant.BASE_URL_BIOMETRIC).biometric(body)
            .enqueue(object : Callback<ResponseBiometric> {
                override fun onResponse(
                    call: Call<ResponseBiometric>,
                    response: Response<ResponseBiometric>
                ) {
                    when (response.code()) {
                        200 -> {
                            result.value = response.body()
                        }

                        else -> {
                            val gson = Gson()
                            val type = object : TypeToken<ResponseBiometric>() {}.type
                            val errorResponse: ResponseBiometric? =
                                gson.fromJson(response.errorBody()!!.charStream(), type)
                            result.value = errorResponse

                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBiometric>, t: Throwable) {
                    val res = ResponseBiometric(null)
                    res.errorMessage = Utils.failureMessage(context, t)
                    result.value = res
                }

            })

        return result
    }
}