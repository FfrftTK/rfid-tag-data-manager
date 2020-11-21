package com.ffrfttk.rfid.TagDataManager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TagDataManagerApplication {
	companion object {
		@JvmStatic fun main(args: Array<String>) {
			runApplication<TagDataManagerApplication>(*args)
		}
	}
}
