package com.ffrfttk.rfid.TagDataManager.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.ffrfttk.rfid.TagDataManager.service.AppUserDetailsService
import org.slf4j.Logger
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest
import kotlin.jvm.Throws

@Component
class JWTProvider(
    private val logger: Logger,
    private val userDetailsService: AppUserDetailsService
) {

    fun generateToken(authentication: Authentication?): String {
        val user = authentication?.principal
            as org.springframework.security.core.userdetails.User
        val issuedAt = Date()
        val notBefore = Date(issuedAt.time)
        val expiresAt = Date(issuedAt.time + SecurityProperties.EXPIRATION_TIME)
        val token = JWT.create()
            .withIssuedAt(issuedAt)
            .withNotBefore(notBefore)
            .withExpiresAt(expiresAt)
            .withSubject(user.username)
            .sign(Algorithm.HMAC256(SecurityProperties.SECRET))
        logger.debug("Published JWT: $token")
        return token
    }

    fun resolveTokenFromRequest(request: ServletRequest?): String? {
        val token = (request as HttpServletRequest)
            .getHeader(SecurityProperties.TOKEN_HEADER)

        if(token == null || !token.startsWith(SecurityProperties.TOKEN_PREFIX)) {
            return null
        }

        return token.substring(SecurityProperties.TOKEN_PREFIX.length)
    }

    private fun verifyToken(token: String): DecodedJWT {
        return JWT.require(Algorithm.HMAC256(SecurityProperties.SECRET))
            .build().verify(token)
    }

    @Throws(UsernameNotFoundException::class)
    fun getAuthentication(token: String): Authentication {
        val userDetail =  userDetailsService.loadUserByUsername(verifyToken(token).subject)
        return UsernamePasswordAuthenticationToken(userDetail, null, userDetail.authorities)
    }

}