package com.ffrfttk.rfid.TagDataManager.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.ffrfttk.rfid.TagDataManager.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenFilter(
    private val algorithm: Algorithm,
    private val userRepository: UserRepository,
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val token = resolveToken(request)
        if(token == null) {
            chain?.doFilter(request, response)
            return
        }

        try {
            authentication(verifyToken(token))
        } catch (e: JWTVerificationException) {
            logger.error("verify token error $e")
            SecurityContextHolder.clearContext()
            (response as HttpServletResponse)
            ?.sendError(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.reasonPhrase)
            chain?.doFilter(request, response)
        }
    }

    private fun resolveToken(request: ServletRequest?): String? {
        val tokenPrefix = "Bearer "
        val token = (request as HttpServletRequest).getHeader("Authorization")
        if(token == null || token.startsWith(tokenPrefix)) {
            return null
        }
        return token.substring(tokenPrefix.length)
    }

    private fun verifyToken(token: String): DecodedJWT {
       return JWT.require(algorithm).build().verify(token)
    }

    private fun authentication(decodedJWT: DecodedJWT) {
        val username = decodedJWT.subject
        val users = userRepository.findByName(username)
        if( users.size == 1 ){
            val userDetail = org.springframework.security.core.userdetails.User(
                users.first().name,
                users.first().password,
                AuthorityUtils.createAuthorityList("ROLE_USER"))

            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(
                userDetail,
                null,
                userDetail.authorities)
        }
    }

}