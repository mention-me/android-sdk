package com.mentionme.lib.Objects

import com.google.gson.JsonObject
import com.mentionme.lib.hasNotNull

class MentionmeTermsLinks(){

    /**
     * The locale code of the terms link
     */
    public var localeCode: String = ""
    /**
     * Link to the terms and conditions in the locale stated
     */
    public var linkToTermsInLocale: String = ""

    constructor(jsonObject: JsonObject): this(){

        if (jsonObject.hasNotNull("localeCode")){
            localeCode = jsonObject.get("localeCode").asString
        }
        if (jsonObject.hasNotNull("linkToTermsInLocale")){
            linkToTermsInLocale = jsonObject.get("linkToTermsInLocale").asString
        }
    }
}