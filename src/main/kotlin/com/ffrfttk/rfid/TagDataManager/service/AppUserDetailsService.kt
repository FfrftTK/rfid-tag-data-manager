package com.ffrfttk.rfid.TagDataManager.service

import com.ffrfttk.rfid.TagDataManager.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if ( username == null || username.isEmpty() ){
            throw UsernameNotFoundException("username is empty");
        }
        val users = userRepository.findByName(username)
        if( users.size == 0 ){
            throw UsernameNotFoundException( username + "is not found");
        }
        return org.springframework.security.core.userdetails.User(
            users.first().name,
            users.first().password,
            AuthorityUtils.createAuthorityList("ROLE_USER"))
    }
}