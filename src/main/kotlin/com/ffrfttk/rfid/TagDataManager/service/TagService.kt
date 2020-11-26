package com.ffrfttk.rfid.TagDataManager.service

import com.ffrfttk.rfid.TagDataManager.entity.Tag
import com.ffrfttk.rfid.TagDataManager.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService (private val tagRepository: TagRepository) {

    fun findAll(): List<Tag> = tagRepository.findAll()

    fun findById(id: Long) = tagRepository.findById(id)

    fun save(tag: Tag) = tagRepository.save(tag)

    fun saveAll(tags: Iterable<Tag>) = tagRepository.saveAll(tags)

    fun delete(id: Long) = tagRepository.deleteById(id)

}