package com.example.splitit

import kotlin.properties.Delegates

class User {
    private var name : String
    private var email : String
    private var id by Delegates.notNull<Int>()

    constructor(id: Int) {
        this.id = id
        this.name = "Sebas"
        this.email = "sebas@yopmail.com"
    }

    constructor(name: String, email: String) {
        this.name = name
        this.email = email
    }

    // TODO
}