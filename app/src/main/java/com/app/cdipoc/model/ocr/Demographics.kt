package com.app.cdipoc.model.ocr

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Demographics (
	var provinsi : String,
	var agama : String,
	var statusPerkawinan : String,
	var tanggalLahir : String,
	var berlakuHingga : String,
	var kotaKabupaten : String,
	var alamat : String,
	var kewarganegaraan : String,
	var nik : String,
	var rtRw : String,
	var nama : String,
	var pekerjaan : String,
	var kecamatan : String,
	var tempatLahir : String,
	var jenisKelamin : String,
	var golonganDarah : String,
	var kelurahanDesa : String
): Parcelable