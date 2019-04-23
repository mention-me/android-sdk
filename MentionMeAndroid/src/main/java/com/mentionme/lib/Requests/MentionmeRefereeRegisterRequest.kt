package com.mentionme.lib.Requests

import com.google.gson.JsonObject
import com.mentionme.lib.Parameters.MentionmeCustomerParameters
import com.mentionme.lib.Parameters.MentionmeReferrerParameters

class MentionmeRefereeRegisterRequest(mentionmeReferrerParameters: MentionmeReferrerParameters, mentionmeCustomerParameters: MentionmeCustomerParameters): MentionmeRequest(){

    var mentionmeReferrerParameters: MentionmeReferrerParameters? = mentionmeReferrerParameters
    var mentionmeCustomerParameters: MentionmeCustomerParameters? = mentionmeCustomerParameters

    init {
        super.method = Method.POST
        super.urlSuffix = "referee/register"
        super.urlEndpoint = "consumer"
    }

    private fun createBodyParameters(){

        val params = JsonObject()

        mentionmeCustomerParameters?.let {

            val customerParams = JsonObject()

            customerParams.addProperty("emailAddress",it.emailAddress)
            customerParams.addProperty("firstname",it.firstname)
            customerParams.addProperty("surname",it.surname)
            if (it.title != null && it.title != "null"){
                customerParams.addProperty("title",it.title!!)
            }
            if (it.uniqueIdentifier != null && it.uniqueIdentifier != "null"){
                customerParams.addProperty("uniqueIdentifer",it.uniqueIdentifier!!)
            }
            if (it.segment != null && it.segment != "null"){
                customerParams.addProperty("segment",it.segment!!)
            }

            params.add("customer",customerParams)
        }

        mentionmeReferrerParameters?.let {

            params.addProperty("referrerToken",it.referrerToken)
            params.addProperty("referrerMentionMeIdentifier",it.referrerMentionMeIdentifier)
        }

        bodyParams = params
    }

    override fun createRequest() {

        createBodyParameters()
        super.createRequest()
    }

}