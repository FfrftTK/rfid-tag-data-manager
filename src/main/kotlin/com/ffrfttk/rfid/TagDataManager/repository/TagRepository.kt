package com.ffrfttk.rfid.TagDataManager.repository

import com.ffrfttk.rfid.TagDataManager.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<Tag, Long> {}