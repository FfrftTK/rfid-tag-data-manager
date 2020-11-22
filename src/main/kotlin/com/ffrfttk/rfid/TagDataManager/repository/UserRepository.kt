package com.ffrfttk.rfid.TagDataManager.repository

import com.ffrfttk.rfid.TagDataManager.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {}