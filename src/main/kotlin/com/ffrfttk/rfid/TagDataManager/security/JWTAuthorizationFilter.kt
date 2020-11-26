package com.ffrfttk.rfid.TagDataManager.security

import com.auth0.jwt.JWT
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.lang.Exception
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.ArrayList

class JWTAuthorizationFilter(authManager: AuthenticationManager)
    : BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain) {

        val token = request.getHeader(SecurityProperties.TOKEN_HEADER)

        if(token == null || !token.startsWith(SecurityProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response)
            return
        }

        getAuthentication(token)?.also {
            SecurityContextHolder.getContext().authentication = it
        }

        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
        return try {
            val decodedJWT = JWT.decode(token)
            UsernamePasswordAuthenticationToken(
                decodedJWT.subject,
                null,
                ArrayList<GrantedAuthority>())
        } catch (e: Exception) {
            return null
        }
    }

}