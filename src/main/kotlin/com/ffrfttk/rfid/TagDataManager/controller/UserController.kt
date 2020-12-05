package com.ffrfttk.rfid.TagDataManager.controller

import com.ffrfttk.rfid.TagDataManager.entity.User
import com.ffrfttk.rfid.TagDataManager.service.UserService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/users")
class UserController (private val userService: UserService) {

    @GetMapping(path = ["{name}"])
    fun findByName(@PathVariable name: String): Optional<User> {
        val user = userService.findByName(name).firstOrNull()
        return Optional.ofNullable(user)
    }

    @PostMapping("signUp")
    fun create(@RequestBody user: User): User {
        user.password = BCryptPasswordEncoder()
            .encode(user.password)
        return userService.save(user)
    }

}
