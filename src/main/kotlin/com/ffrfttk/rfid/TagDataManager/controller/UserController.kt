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
    val privateKey = "secret"

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

    @PostMapping(path = ["login"])
    fun login(@RequestBody user: User): User {
        val res = createToken(user)
        return userService.save(user)
    }

    // Utils
    fun createToken(user: User) : String {
        lateinit var token: String
        try {
            val algorithm = Algorithm.HMAC256(privateKey)
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar.add(Calendar.SECOND, 30)
            val expireTime = calendar.time
            token = JWT.create()
                .withIssuer("auth0")
                .withClaim("name", user.name)
                .withExpiresAt(expireTime)
                .sign(algorithm)
        } catch(e: JWTCreationException){
            return "Invalid Token"
        }
        return token
    }

    fun authenticateToken(token: String) {
        try {
            val algorithm = Algorithm.HMAC256(privateKey)
            val jwtVerifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build()
            jwtVerifier.verify(token)
        } catch (e: JWTCreationException) {
            println("Invalid Token")
        }
    }

}
