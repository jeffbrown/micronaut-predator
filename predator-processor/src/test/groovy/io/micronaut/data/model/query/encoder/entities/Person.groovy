package io.micronaut.data.model.query.encoder.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Person {

    @GeneratedValue
    @Id
    Long id

    String name

    int age
}
