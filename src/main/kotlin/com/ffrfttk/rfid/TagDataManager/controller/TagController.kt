package com.ffrfttk.rfid.TagDataManager.controller

import com.ffrfttk.rfid.TagDataManager.entity.Tag
import com.ffrfttk.rfid.TagDataManager.service.TagService
import com.ffrfttk.rfid.TagDataManager.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/tags")
class TagController (
    @Autowired
    private val tagService: TagService,

    @Autowired
    private val userService: UserService,
) {
    @GetMapping("")
    fun showStatus(): List<Tag> {
        return userService.findUserFromAuthentication()?.tags ?: listOf()
    }

    @PostMapping("")
    fun create(@RequestBody tag: Tag): Tag {
        val user = userService.findUserFromAuthentication()
            ?: return throw UsernameNotFoundException("Invalid User")
        tag.user = user
        return tagService.save(tag)
    }
}