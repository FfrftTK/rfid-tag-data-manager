package com.ffrfttk.rfid.TagDataManager.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

class JWTAuthenticationFilter : UsernamePasswordAuthenticationFilter() {
    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse?): Authentication {
        val name = request.getParameterValues("name").firstOrNull()
        val password = request.getParameterValues("password").firstOrNull()
        val authRequest = UsernamePasswordAuthenticationToken(name, password)
        setDetails(request, authRequest)
        return authenticationManager.authenticate(authRequest)
    }
}