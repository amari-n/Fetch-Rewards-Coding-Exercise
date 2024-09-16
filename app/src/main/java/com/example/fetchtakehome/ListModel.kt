package com.example.fetchtakehome

class ListModel {
    var id : Int
    var listId : Int
    var name : String

    constructor(id : Int, listId : Int, name : String) {
        this.id = id
        this.listId = listId
        this.name = name
    }

    override fun toString(): String {
        return "ListId $listId: id=$id, name='$name'"
    }
}