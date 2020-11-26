package com.ffrfttk.rfid.TagDataManager.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

class JWTAuthenticationFilter(
    private val authManager: AuthenticationManager,
    private val algorithm: Algorithm)
    : UsernamePasswordAuthenticationFilter() {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse?): Authentication {
        val name = request.getParameterValues("name").firstOrNull()
        val password = request.getParameterValues("passwordRaw").firstOrNull()
        val x = obtainUsername(request)
        val authRequest = UsernamePasswordAuthenticationToken(name, password)
        setDetails(request, authRequest)
        return authenticationManager.authenticate(authRequest)
    }
}