package com.example.shoppinglistapp.domain

data class EditItem(
    val id: Int = DEFAULT_ID,
    val name: String,
    val count: Int
) {
    companion object {
        val DEFAULT_ID = -1
    }
}