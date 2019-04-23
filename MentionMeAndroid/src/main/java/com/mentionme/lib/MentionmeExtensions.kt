package com.mentionme.lib

import com.google.gson.JsonObject

class MentionmeExtensions

fun JsonObject.hasNotNull(memberName: String): Boolean{
    if (this.has(memberName) && !this.get(memberName).isJsonNull){
        return true
    }
    return false
}