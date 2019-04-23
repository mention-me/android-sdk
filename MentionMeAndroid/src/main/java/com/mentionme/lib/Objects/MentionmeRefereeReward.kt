package com.mentionme.lib.Objects

import com.google.gson.JsonObject
import com.mentionme.lib.hasNotNull

class MentionmeRefereeReward(){

    /**
     * Description of the reward
     */
    public var descriptionRefereeReward: String = ""
    /**
     * Reward coupon code
     */
    public var couponCode: String = ""
    /**
     * Reward security code (if appropriate)
     */
    public var securityCode: String = ""
    /**
     * The reward amount
     */
    public var amount: String = ""

    constructor(jsonObject: JsonObject): this(){

        if (jsonObject.hasNotNull("description")){
            descriptionRefereeReward = jsonObject.get("description").asString
        }
        if (jsonObject.hasNotNull("couponCode")){
            couponCode = jsonObject.get("couponCode").asString
        }
        if (jsonObject.hasNotNull("securityCode")){
            securityCode = jsonObject.get("securityCode").asString
        }
        if (jsonObject.hasNotNull("amount")){
            amount = jsonObject.get("amount").asString
        }

    }


}