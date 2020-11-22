package com.ffrfttk.rfid.TagDataManager.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User (
    @Id
    @SequenceGenerator(name = "users_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String? = "",

    var description: String? = "",

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = LocalDateTime.now()
)