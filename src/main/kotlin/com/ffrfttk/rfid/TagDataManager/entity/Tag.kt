package com.ffrfttk.rfid.TagDataManager.entity

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tags")
@JsonIdentityInfo(property = "@id", generator = ObjectIdGenerators.UUIDGenerator::class)
data class Tag (
    @Id
    @SequenceGenerator(name = "tags_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var code: String = "",

    var name: String? = "",

    var description: String? = "",
    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne()
    @JoinColumn(name = "user_id")
    var user: User? = User(id = 1,)
)