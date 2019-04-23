package com.mentionme.lib.Objects

import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONObject

class MentionmeError(){

    /**
     * The array of errors of the response
     */
    public var errors: JSONArray = JSONArray()
    /**
     * The status code of the response
     */
    public var statusCode: Int = 0

    constructor(errors: JsonArray, code: Int): this(){

        for (error in errors){
            val objc = JSONObject()
            objc.put("message",error.asJsonObject.get("message").asString)
            this.errors.put(objc)
        }
        this.statusCode = code
    }
}