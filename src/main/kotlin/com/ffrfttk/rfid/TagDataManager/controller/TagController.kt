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
    fun getAllTags(): List<Tag> {
        return userService.findUserFromAuthentication()?.tags ?: listOf()
    }

    @PostMapping("")
    fun register(@RequestBody tag: Tag): Tag {
        val user = userService.findUserFromAuthentication()
            ?: return throw UsernameNotFoundException("Invalid User")
        tag.user = user
        return tagService.save(tag)
    }

    @PostMapping("all")
    fun registerAll(@RequestBody tags: MutableList<Tag>): List<Tag> {
        val user = userService.findUserFromAuthentication()
            ?: return throw UsernameNotFoundException("Invalid User")
        tags.map { x -> x.user = user }

        return tagService.saveAll(tags)
    }

    @PutMapping("")
    fun updateTag(@RequestBody tag: Tag): Tag? {
        val tagOld = tagService.findById(tag.id ?: 0)
        if (tagOld.isEmpty) {
            return null
        }

        val user = userService.findUserFromAuthentication()
            ?: return throw UsernameNotFoundException("Invalid User")
        tag.user = user
        // TODO: This param should be modified with DB functions
        tag.createdAt = tagOld.get().createdAt

        return tagService.save(tag)
    }
}