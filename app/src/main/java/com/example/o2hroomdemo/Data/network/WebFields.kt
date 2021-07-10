package com.example.o2hroomdemo.data.network

class WebFields {

    //Todo: Api Call Releted Comman and static Data
    companion object {

        const val BASE_URL = "http://18.188.115.230/api/" //For Live
        const val IMAGE_BASE_URL = "http://18.188.115.230/storage/app/"

        const val HOME_PATH_PARAM = "home"// g


        //Common request param
        const val RESPONSE_MESSAGE = "message"
        const val RESPONSE_ERRORS = "errors"

        const val REQUEST_USER_NAME = "username"
        const val REQUEST_EMAIL = "email"
        const val REQUEST_MOBILE = "mobile"
        const val REQUEST_PASSWORD = "password"
        const val REQUEST_DEVICE_ID = "device_id"

        const val REQUEST_AUTH = "Authorization"
        const val REQUEST_ACCEPT = "Accept"
        const val REQUEST_APPL_JSON = "application/json"
        const val REQUEST_BEARER = "Bearer "

        //editprofile
        const val REQUEST_NAME = "name"
        const val REQUEST_ADDRESS = "address"
        const val REQUEST_GENDER = "gender"
        const val REQUEST_DOB = "dob"
        const val REQUEST_IMAGE = "image"
        const val REQUEST_COMPANY_IMAGE = "companyimage"


    }
}