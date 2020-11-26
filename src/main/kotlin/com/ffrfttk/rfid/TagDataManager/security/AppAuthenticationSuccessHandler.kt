package com.ffrfttk.rfid.TagDataManager.security

import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AppAuthenticationSuccessHandler(
    private val jwtProvider: JWTProvider,
    private val logger: Logger
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?) {
        // Check response status
        if (response?.isCommitted == true) {
            logger.info("Response has already been committed")
            return
        }

        // Get user info
        val token = SecurityProperties.TOKEN_PREFIX + jwtProvider.generateToken(authentication)
        response?.setHeader(SecurityProperties.TOKEN_HEADER, token)
        response?.status = HttpStatus.OK.value()
    }
}