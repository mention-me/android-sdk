package com.mentionme.lib.Objects

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mentionme.lib.hasNotNull

class MentionmeShareLink(){

    /**
     * The type of share mechanism (e.g. Facebook, Twitter, Open link, Email link)
     */
    public var type: String = ""
    /**
     * The protocol (if available) for the share
     */
    public var protocolShareLink: String = ""
    /**
     * The url to include in the share
     */
    public var url: String = ""
    /**
     * The default message to include in the share
     */
    public var defaultShareMessage: String = ""
    /**
     * An example of the share URL you could use to initiate this share. You are free to implement this your own way if you wish
     */
    public var exampleImplementation: String = ""

    constructor(jsonObject: JsonObject): this(){

        if (jsonObject.hasNotNull("type")){
            type = jsonObject.get("type").asString
        }
        if (jsonObject.hasNotNull("protocol")){
            protocolShareLink = jsonObject.get("protocol").asString
        }
        if (jsonObject.hasNotNull("url")){
            url = jsonObject.get("url").asString
        }
        if (jsonObject.hasNotNull("defaultShareMessage")){
            defaultShareMessage = jsonObject.get("defaultShareMessage").asString
        }
        if (jsonObject.hasNotNull("exampleImplementation")){
            exampleImplementation = jsonObject.get("exampleImplementation").asString
        }


    }

}


