package com.ffrfttk.rfid.TagDataManager.security

import com.ffrfttk.rfid.TagDataManager.service.AppUserDetailsService
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler


@EnableWebSecurity
class SecurityConfig(
    @Autowired
    private val appUserDetailsService: AppUserDetailsService,

    @Autowired
    private val logger: Logger
) : WebSecurityConfigurerAdapter() {

    val secretKey = "secret"

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(appUserDetailsService)
            ?.passwordEncoder(BCryptPasswordEncoder())
    }

    override fun configure(web: WebSecurity?) {
        super.configure(web)
    }

    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests()
            .mvcMatchers("/api/v1/users/signUp").permitAll()
            .anyRequest().authenticated()
            .and()
            // login
            .formLogin()
            .loginProcessingUrl("/login").permitAll()
            .usernameParameter("name")
            .passwordParameter("passwordRaw")
            .successHandler(authenticationSuccessHandler())
//            .failureHandler(authenticationFailureHandler())
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    fun authenticationSuccessHandler(): AuthenticationSuccessHandler? {
        return AppAuthenticationSuccessHandler(secretKey, logger)
    }

    fun authenticationFailureHandler(): AuthenticationFailureHandler? {
        return AppAuthenticationFailureHandler(logger)
    }


}