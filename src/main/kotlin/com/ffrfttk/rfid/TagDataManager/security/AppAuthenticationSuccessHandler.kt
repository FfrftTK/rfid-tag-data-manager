package com.ffrfttk.rfid.TagDataManager.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.util.*
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AppAuthenticationSuccessHandler(secretKey: String, private val logger: Logger) : AuthenticationSuccessHandler {
    companion object {
        val expirationTime: Long = TimeUnit.MINUTES.toMillis(10)
    }
    private val algorithm = Algorithm.HMAC256(secretKey)

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?) {
        if (response?.isCommitted == true) {
            logger.info("Response has already been committed")
            return
        }
        setToken(response, generateToken(authentication))
        response?.status = HttpStatus.OK.value()
        clearAuthenticationAttributes(request)
    }


    private fun generateToken(authentication: Authentication?): String {
        val user = authentication?.principal
            as org.springframework.security.core.userdetails.User
        val issuedAt = Date()
        val notBefore = Date(issuedAt.time)
        val expiresAt = Date(issuedAt.time + expirationTime)
        val token = JWT.create()
            .withIssuedAt(issuedAt)
            .withNotBefore(notBefore)
            .withExpiresAt(expiresAt)
            .withSubject(user.username)
            .withSubject("1")
            .sign(this.algorithm);
        logger.debug(token)
        return token
    }

    private fun setToken(response: HttpServletResponse?, token: String) {
        response?.setHeader("Authorization", "Barer $token")
    }

    /**
     * Removes temporary authentication-related data which may have been stored in the
     * session during the authentication process.
     */
    private fun clearAuthenticationAttributes(request: HttpServletRequest?) {
        request?.session?.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)
    }

}