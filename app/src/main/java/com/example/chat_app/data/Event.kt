package com.example.chat_app.data

open class Event<out T>(val content: T) {
    var handled = false
    fun getContentOrNull(): T? {
        return if (handled) null
        else {
            handled = true
            content
        }
    }

}