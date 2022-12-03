package com.app.cdipoc.model.demography
import com.google.gson.annotations.SerializedName


data class DemographyRequest(
    val alamat: String,
    @SerializedName("customer_Id")
    val customerId: String,
    @SerializedName("ip_user")
    val ipUser: String,
    @SerializedName("jenis_klmin")
    val jenisKlmin: String,
    @SerializedName("nama_lgkp")
    val namaLgkp: String,
    val nik: String,
    @SerializedName("no_kab")
    val noKab: String,
    @SerializedName("no_kec")
    val noKec: String,
    @SerializedName("no_kel")
    val noKel: String,
    @SerializedName("no_prop")
    val noProp: String,
    @SerializedName("no_rt")
    val noRt: String,
    @SerializedName("no_rw")
    val noRw: String,
    val password: String,
    @SerializedName("tgl_lhr")
    val tglLhr: String,
    @SerializedName("tmpt_lhr")
    val tmptLhr: String,
    val transactionId: String,
    val transactionSource: String,
    val treshold: Int,
    @SerializedName("user_id")
    val userId: String
)