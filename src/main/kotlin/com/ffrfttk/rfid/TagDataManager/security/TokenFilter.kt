package com.ffrfttk.rfid.TagDataManager.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class TokenFilter(private val algorithm: Algorithm) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
    }

    private fun resolveToken(request: ServletRequest?): String? {
        val tokenPrefix = "Bearer "
        val token = (request as HttpServletRequest).getHeader("Authorization")
        if(token == null || token.startsWith(tokenPrefix)){
            return null
        }
        return token.substring(tokenPrefix.length)
    }

    private fun verifyToken(token: String): DecodedJWT {
       return JWT.require(algorithm).build().verify(token)
    }

    private fun authentication(decodedJWT: DecodedJWT) {
        decodedJWT.subject
    }

}