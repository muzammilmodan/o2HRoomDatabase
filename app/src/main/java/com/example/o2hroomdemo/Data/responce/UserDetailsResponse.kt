package com.example.o2hroomdemo.data.responce

data class UserDetailsResponse(
    val `data`: UserDetailsData
)

data class UserDetailsData(
    val complaint_category: List<ComplaintCategory>,
    val userdata: Userdata
)

data class ComplaintCategory(
    val complaint_category: String,
    val id: Int,
    val value: Int
) {
    override fun toString(): String {
        return complaint_category
    }
}

data class Userdata(
    val address: String,
    val city: String,
    val companyname: String,
    val device_id: String,
    val dob: String,
    val email: String,
    val gender: String,
    val image: String,
    val mobile: String,
    val name: String,
    val role_id: Int,
    val state: String,
    val status: String,
    val verified: Int
)