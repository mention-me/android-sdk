package com.mentionme.lib.Objects

import com.google.gson.JsonObject
import com.mentionme.lib.hasNotNull

class MentionmeDashboardReward(){

    /**
     * Friendly description of the reward for the intended recipient e.g. "John, give your friends {{referee-their-reward}} when they {{referee-action-for-referrer}} on {{selling-brand}}." e.g. "John, give your friends 20% off when they order for the first time on [BRAND]" e.g. "You'll get £20 for each friend you refer"
     */
    public var descriptionDashboardReward: String = ""
    /**
     * Summary of the reward itself e.g. "20% off" e.g. "£20 gift card"
     */
    public var summary: String = ""
    /**
     * The reward amount
     */
    public var amount: String = ""
    /**
     * Status of the reward eg Pending, Cancelled, Given by email
     */
    public var status: String = ""
    /**
     * Description of who they referred to get the reward
     */
    public var forReferring: String = ""

    constructor(jsonObject: JsonObject): this(){

        if (jsonObject.hasNotNull("description")){
            descriptionDashboardReward = jsonObject.get("description").asString
        }
        if (jsonObject.hasNotNull("summary")){
            summary = jsonObject.get("summary").asString
        }
        if (jsonObject.hasNotNull("amount")){
            amount = jsonObject.get("amount").asString
        }
        if (jsonObject.hasNotNull("status")){
            status = jsonObject.get("status").asString
        }
        if (jsonObject.hasNotNull("forReferring")){
            forReferring = jsonObject.get("forReferring").asString
        }
    }

}