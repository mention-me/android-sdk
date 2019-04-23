package com.mentionme.lib.Objects

import com.google.gson.JsonObject
import com.mentionme.lib.hasNotNull

class MentionmeOffer(){

    /**
     * The identifier of the offer
     */
    public var id: Int = 0
    /**
     * The locale code of the offer
     */
    public var localeCode: String = ""
    /**
     * The headline description for the offer e.g. "You can get a £20 gift card if you refer a friend to ..."
     */
    public var headline: String = ""
    /**
     * The details of the offer e.g. "Give your friends a 20% off introductory offer"
     */
    public var descriptionOffer: String = ""
    /**
     * The details of the reward for the referrer
     */
    public var referrerReward: MentionmeReward? = null
    /**
     * The details of the reward for the referee
     */
    public var refereeReward: MentionmeReward? = null


    constructor(offerObject: JsonObject): this(){

        if (offerObject.hasNotNull("id")){
            id = offerObject.get("id").asInt
        }
        if (offerObject.hasNotNull("localeCode")){
            localeCode = offerObject.get("localeCode").asString
        }
        if (offerObject.hasNotNull("headline")){
            headline = offerObject.get("headline").asString
        }
        if (offerObject.hasNotNull("description")){
            descriptionOffer = offerObject.get("description").asString
        }
        if (offerObject.hasNotNull("referrerReward")){
            referrerReward = MentionmeReward(offerObject.getAsJsonObject("referrerReward"))
        }
        if (offerObject.hasNotNull("refereeReward")){
            refereeReward = MentionmeReward(offerObject.getAsJsonObject("refereeReward"))
        }

    }

}