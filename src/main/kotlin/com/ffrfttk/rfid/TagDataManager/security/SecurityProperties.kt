package com.ffrfttk.rfid.TagDataManager.security

import java.util.concurrent.TimeUnit


class SecurityProperties {
    companion object {
        const val SECRET = "secret"
        val EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(300)
        const val TOKEN_HEADER = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
        const val SIGN_IN_URL = "/login"
        const val SIGN_UP_URL = "/api/v1/users/signUp"
    }
}