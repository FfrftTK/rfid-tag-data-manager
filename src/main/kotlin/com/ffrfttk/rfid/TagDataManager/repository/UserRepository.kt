package com.ffrfttk.rfid.TagDataManager.repository

import com.ffrfttk.rfid.TagDataManager.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    @Query("select e from User e where e.name = ?1")
    fun findByName(name: String): MutableList<User>
}