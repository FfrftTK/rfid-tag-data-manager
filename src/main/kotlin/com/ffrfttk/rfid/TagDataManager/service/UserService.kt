package com.ffrfttk.rfid.TagDataManager.service

import com.ffrfttk.rfid.TagDataManager.entity.User
import com.ffrfttk.rfid.TagDataManager.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService (private val userRepository: UserRepository) {

    fun findById(id: Long) = userRepository.findById(id)

    fun findByName(name: String) = userRepository.findByName(name)

    fun save(user: User) = userRepository.save(user)
}