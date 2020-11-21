package com.ffrfttk.rfid.TagDataManager.entity

import javax.persistence.*

@Entity
@Table(name = "tags")
data class Tag (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long? = null,

    var tagId: String,

    var tagName: String = "",

    var description: String = "")