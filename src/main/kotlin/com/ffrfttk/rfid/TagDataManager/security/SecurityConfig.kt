package com.ffrfttk.rfid.TagDataManager.security

import com.ffrfttk.rfid.TagDataManager.service.AppUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@EnableWebSecurity
class SecurityConfig(
    @Autowired
    private val appUserDetailsService: AppUserDetailsService,

    @Autowired
    private val appAuthenticationSuccessHandler: AppAuthenticationSuccessHandler,

    @Autowired
    private val appAuthenticationFailureHandler: AppAuthenticationFailureHandler,

    @Autowired
    private val tokenFilter: TokenFilter
) : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(appUserDetailsService)
            ?.passwordEncoder(BCryptPasswordEncoder())
    }

    override fun configure(web: WebSecurity?) {
        super.configure(web)
    }

    override fun configure(http: HttpSecurity?) {
        http!!
            // Basic Auth
            .httpBasic().disable()
            // CSRF
            .csrf().disable()
            // Session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            // Login
            .and()
            .formLogin()
            .loginProcessingUrl(SecurityProperties.SIGN_IN_URL)
            .usernameParameter("name")
            .passwordParameter("password")
            .successHandler(appAuthenticationSuccessHandler)
            .failureHandler(appAuthenticationFailureHandler)
            // Authorizations
            .and()
            .authorizeRequests()
            .mvcMatchers(SecurityProperties.SIGN_UP_URL).permitAll()
            .anyRequest().authenticated()
            // Filters
            .and()
            .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

}