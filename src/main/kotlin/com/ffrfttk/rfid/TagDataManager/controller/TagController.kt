package com.ffrfttk.rfid.TagDataManager.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/tags")
class TagController {
    @GetMapping("")
    fun showStatus(): String {
        return "hello"
    }
}