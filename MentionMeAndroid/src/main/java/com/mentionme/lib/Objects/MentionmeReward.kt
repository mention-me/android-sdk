package com.mentionme.lib.Objects

import com.google.gson.JsonObject
import com.mentionme.lib.hasNotNull

class MentionmeReward(){

    /**
     * Friendly description of the reward for the intended recipient e.g. "John, give your friends {{referee-their-reward}} when they {{referee-action-for-referrer}} on {{selling-brand}}." e.g. "John, give your friends 20% off when they order for the first time on [BRAND]" e.g. "You'll get £20 for each friend you refer"
     */
    public var descriptionReward: String = ""
    /**
     * Summary of the reward itself e.g. "20% off" e.g. "£20 gift card"
     */
    public var summary: String = ""
    /**
     * The reward amount
     */
    public var amount: String = ""

    constructor(rewardObject: JsonObject): this(){

        if (rewardObject.hasNotNull("description")){
            descriptionReward = rewardObject.get("description").asString
        }
        if (rewardObject.hasNotNull("summary")){
            summary = rewardObject.get("summary").asString
        }
        if (rewardObject.hasNotNull("amount")){
            amount = rewardObject.get("amount").asString
        }

    }

}