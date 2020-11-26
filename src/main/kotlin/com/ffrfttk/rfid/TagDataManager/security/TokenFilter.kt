package com.ffrfttk.rfid.TagDataManager.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

@Component
class TokenFilter(private val jwtProvider: JWTProvider, ) : GenericFilterBean() {
    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse?,
        chain: FilterChain?) {

        val token = jwtProvider.resolveTokenFromRequest(request)

        if(token != null) {
            SecurityContextHolder.getContext().authentication =
                jwtProvider.getAuthentication(token)
        }

        chain?.doFilter(request, response)
    }
}