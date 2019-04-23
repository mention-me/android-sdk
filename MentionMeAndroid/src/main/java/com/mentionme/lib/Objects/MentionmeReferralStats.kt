package com.mentionme.lib.Objects

import com.google.gson.JsonObject
import com.mentionme.lib.hasNotNull

class MentionmeReferralStats(){

    /**
     * Number of successful referrals
     */
    public var successfulReferrals: Int? = null
    /**
     * Number of invitations made
     */
    public var invitations: Int? = null
    /**
     * Number of clicks on invites
     */
    public var clicksOnInvites: Int? = null

    constructor(jsonObject: JsonObject): this(){

        if (jsonObject.hasNotNull("successfulReferrals")){
            successfulReferrals = jsonObject.get("successfulReferrals").asInt
        }
        if (jsonObject.hasNotNull("invitations")){
            invitations = jsonObject.get("invitations").asInt
        }
        if (jsonObject.hasNotNull("clicksOnInvites")){
            clicksOnInvites = jsonObject.get("clicksOnInvites").asInt
        }

    }

}