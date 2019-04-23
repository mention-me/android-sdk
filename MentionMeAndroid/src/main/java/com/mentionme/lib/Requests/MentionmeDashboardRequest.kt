package com.mentionme.lib.Requests

import com.mentionme.lib.Parameters.MentionmeDashboardParameters

class MentionmeDashboardRequest(mentionmeDashboardParameters: MentionmeDashboardParameters): MentionmeRequest(){

    var mentionmeDashboardParameters: MentionmeDashboardParameters? = mentionmeDashboardParameters

    init {
        super.method = Method.GET
        super.urlSuffix = "referrer/dashboard"
        super.urlEndpoint = "consumer"
    }

    private fun createQueryParameters(){

        val params = HashMap<String, Any>()

        mentionmeDashboardParameters?.let{

            params.put("emailAddress",it.emailAddress)
            if (it.uniqueCustomerIdentifier != null && it.uniqueCustomerIdentifier != "null"){
                params.put("uniqueCustomerIdentifier",it.uniqueCustomerIdentifier!!)
            }

        }

        super.queryParams = params
    }

    override fun createRequest() {

        createQueryParameters()
        super.createRequest()
    }

}