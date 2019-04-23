package com.mentionme.lib.Objects

import com.google.gson.JsonObject
import com.mentionme.lib.hasNotNull

class MentionmeContentCollectionLink(){

    /**
     * Relationship of the link between the two resources
     */
    public var relationship: String = ""
    /**
     * The target resource being linked
     */
    public var resource: ArrayList<MentionmeContent>? = null

    constructor(jsonObject: JsonObject): this(){

        if (jsonObject.hasNotNull("relationship")){
            relationship = jsonObject.get("relationship").asString
        }
        if (jsonObject.hasNotNull("resource") && jsonObject.get("resource").isJsonArray){
            resource = ArrayList()
            for (resourceObject in jsonObject.getAsJsonArray("resource")){
                resource?.add(MentionmeContent(resourceObject.asJsonObject))
            }

        }

    }

}