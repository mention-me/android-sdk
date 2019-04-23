package com.mentionme.lib.Requests

import com.google.gson.JsonObject
import com.mentionme.lib.Parameters.MentionmeCustomerParameters
import com.mentionme.lib.Parameters.MentionmeOrderParameters

class MentionmeOrderRequest(mentionmeOrderParameters: MentionmeOrderParameters, mentionmeCustomerParameters: MentionmeCustomerParameters): MentionmeRequest(){

    var mentionmeOrderParameters: MentionmeOrderParameters? = mentionmeOrderParameters
    var mentionmeCustomerParameters: MentionmeCustomerParameters? = mentionmeCustomerParameters

    init {
        super.method = Method.POST
        super.urlSuffix = "order"
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

        mentionmeOrderParameters?.let {

            val orderParams = JsonObject()

            orderParams.addProperty("orderIdentifier",it.orderIdentifier)
            orderParams.addProperty("total",it.total)
            orderParams.addProperty("currencyCode",it.currencyCode)
            orderParams.addProperty("dateString",it.dateString)
            if (it.couponCode != null && it.couponCode != "null"){
                orderParams.addProperty("couponCode",it.couponCode!!)
            }


            params.add("order",orderParams)
        }

        super.bodyParams = params

    }

    override fun createRequest() {
        createBodyParameters()
        super.createRequest()
    }

}