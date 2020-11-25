package com.ffrfttk.rfid.TagDataManager.security

import java.util.concurrent.TimeUnit

class SecurityConstants {
    companion object {
        const val SECRET = "secret"
        val EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(10)
        const val TOKEN_HEADER = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
        const val SIGN_UP_URL = "/api/v1/users/signUp"
    }
}