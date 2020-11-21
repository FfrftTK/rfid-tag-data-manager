package com.ffrfttk.rfid.TagDataManager.controller

import com.ffrfttk.rfid.TagDataManager.entity.Tag
import com.ffrfttk.rfid.TagDataManager.service.TagService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/tags")
class TagController (private val tagService: TagService) {
    @GetMapping("")
    fun showStatus(): String {
        return "hello"
    }

    @PostMapping("")
    fun create(@RequestBody tag: Tag): Tag{
        println(tag)
        return tagService.save(tag)
    }
}