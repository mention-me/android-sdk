package com.mentionme.lib.Objects

import com.google.gson.JsonObject
import com.mentionme.lib.hasNotNull

class MentionmeContent(){

    /**
     * Token
     */
    public var token: String = ""
    /**
     * The headline
     */
    public var headline: String = ""

    constructor(jsonObject: JsonObject): this(){

        if (jsonObject.hasNotNull("token")){
            token = jsonObject.get("token").asString
        }
        if (jsonObject.hasNotNull("headline")){
            headline = jsonObject.get("headline").asString
        }

    }

}