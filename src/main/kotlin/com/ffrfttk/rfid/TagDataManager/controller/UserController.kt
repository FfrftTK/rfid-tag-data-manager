package com.ffrfttk.rfid.TagDataManager.controller

import com.ffrfttk.rfid.TagDataManager.entity.User
import com.ffrfttk.rfid.TagDataManager.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/users")
class UserController (private val userService: UserService) {

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long): Optional<User> {
        return userService.findById(id)
    }

    @PostMapping("")
    fun create(@RequestBody user: User): User {
        return userService.save(user)
    }

}