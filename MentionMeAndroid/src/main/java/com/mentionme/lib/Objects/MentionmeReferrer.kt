package com.mentionme.lib.Objects

import com.google.gson.JsonObject
import com.mentionme.lib.hasNotNull

class MentionmeReferrer(){

    /**
     * Our identifier for the referrer identified, the customer ID
     */
    public var referrerMentionMeIdentifier: Int = 0
    /**
     * Token used to identify the referrer uniquely (flowId)
     */
    public var referrerToken: String = ""
    /**
     * Identify the offer
     */
    public var referrerOfferIdentifier: Int = 0
    /**
     * Description of the offer and rewards which this referrer is able to offer
     */
    public var offer: MentionmeOffer? = null

    constructor(jsonObject: JsonObject): this(){

        if (jsonObject.hasNotNull("referrerMentionMeIdentifier")){
            referrerMentionMeIdentifier = jsonObject.get("referrerMentionMeIdentifier").asInt
        }
        if (jsonObject.hasNotNull("referrerToken")){
            referrerToken = jsonObject.get("referrerToken").asString
        }
        if (jsonObject.hasNotNull("referrerOfferIdentifier")){
            referrerOfferIdentifier = jsonObject.get("referrerOfferIdentifier").asInt
        }
        if (jsonObject.hasNotNull("offer") && jsonObject.get("offer").isJsonObject){
            offer = MentionmeOffer(jsonObject.getAsJsonObject("offer").asJsonObject)
        }

    }

}