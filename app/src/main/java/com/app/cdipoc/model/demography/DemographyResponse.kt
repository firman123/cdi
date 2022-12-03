package com.app.cdipoc.model.demography
import com.google.gson.annotations.SerializedName


    data class DemographyResponse(
    val content: List<Content>,
    val firstPage: Boolean,
    val lastPage: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val size: Int,
    val sort: Any,
    val totalElements: Int
)

data class Content(
    @SerializedName("AGAMA")
    val aGAMA: String,
    @SerializedName("ALAMAT")
    val aLAMAT: String,
    @SerializedName("DUSUN")
    val dUSUN: String,
    @SerializedName("GOL_DARAH")
    val gOLDARAH: String,
    @SerializedName("JENIS_KLMIN")
    val jENISKLMIN: String,
    @SerializedName("JENIS_PKRJN")
    val jENISPKRJN: String,
    @SerializedName("KAB_NAME")
    val kABNAME: String,
    @SerializedName("KEC_NAME")
    val kECNAME: String,
    @SerializedName("KEL_NAME")
    val kELNAME: String,
    @SerializedName("NAMA_LGKP")
    val nAMALGKP: String,
    @SerializedName("NAMA_LGKP_AYAH")
    val nAMALGKPAYAH: String,
    @SerializedName("NAMA_LGKP_IBU")
    val nAMALGKPIBU: String,
    @SerializedName("NIK")
    val nIK: Long,
    @SerializedName("NIK_AYAH")
    val nIKAYAH: String,
    @SerializedName("NIK_IBU")
    val nIKIBU: String,
    @SerializedName("NO_AKTA_CRAI")
    val nOAKTACRAI: String,
    @SerializedName("NO_AKTA_KWN")
    val nOAKTAKWN: String,
    @SerializedName("NO_AKTA_LHR")
    val nOAKTALHR: String,
    @SerializedName("NO_KAB")
    val nOKAB: String,
    @SerializedName("NO_KEC")
    val nOKEC: String,
    @SerializedName("NO_KEL")
    val nOKEL: String,
    @SerializedName("NO_KK")
    val nOKK: String,
    @SerializedName("NO_PROP")
    val nOPROP: String,
    @SerializedName("NO_RT")
    val nORT: String,
    @SerializedName("NO_RW")
    val nORW: String,
    @SerializedName("PDDK_AKH")
    val pDDKAKH: String,
    @SerializedName("PROP_NAME")
    val pROPNAME: String,
    @SerializedName("STAT_HBKEL")
    val sTATHBKEL: String,
    @SerializedName("STATUS_KAWIN")
    val sTATUSKAWIN: String,
    @SerializedName("TGL_CRAI")
    val tGLCRAI: String,
    @SerializedName("TGL_KWN")
    val tGLKWN: String,
    @SerializedName("TGL_LHR")
    val tGLLHR: String,
    @SerializedName("TMPT_LHR")
    val tMPTLHR: String
)