package com.ffrfttk.rfid.TagDataManager.entity

import javax.persistence.*

@Entity
@Table(name = "tags")
data class Tag (
    @Id
    var id: String = "",

    var name: String? = "",

    var description: String? = "")