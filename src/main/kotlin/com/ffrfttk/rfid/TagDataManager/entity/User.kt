package com.ffrfttk.rfid.TagDataManager.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
//@JsonIdentityInfo(property = "@id", generator = ObjectIdGenerators.UUIDGenerator::class)
data class User (

    @Id
    var name: String = "",

    @Transient
    var passwordRaw: String? = "",

//    @JsonIgnore
    @Column(name = "password")
    var password: String = "",

    var description: String? = "",

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = LocalDateTime.now(),

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = [CascadeType.ALL])
    @JsonManagedReference
    var tags: MutableList<Tag> = mutableListOf()
)