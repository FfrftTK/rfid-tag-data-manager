package com.ffrfttk.rfid.TagDataManager.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.ffrfttk.rfid.TagDataManager.entity.User
import com.ffrfttk.rfid.TagDataManager.service.UserService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/users")
class UserController (private val userService: UserService) {

    @GetMapping(path = ["{id}"])
    fun findById(@PathVariable id: Long): Optional<User> {
        return userService.findById(id)
    }

    @PostMapping("signUp")
    fun create(@RequestBody user: User): User {
        user.password = BCryptPasswordEncoder()
            .encode(user.passwordRaw)
        return userService.save(user)
    }

}
