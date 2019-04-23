package com.mentionme.lib.Requests

import com.mentionme.lib.Parameters.MentionmeReferrerNameParameters

class MentionmeReferrerByNameRequest(mentionmeReferrerNameParameters: MentionmeReferrerNameParameters): MentionmeRequest(){

    var mentionmeReferrerNameParameters: MentionmeReferrerNameParameters? = mentionmeReferrerNameParameters

    init {
        super.method = Method.GET
        super.urlSuffix = "referrer/search"
        super.urlEndpoint = "consumer"
    }

    private fun createQueryParameters(){

        val params = HashMap<String, Any>()

        mentionmeReferrerNameParameters?.let {

            params.put("name",it.name)
            if (it.email != null && it.email != "null"){
                params.put("email",it.email!!)
            }

        }

        super.queryParams = params

    }

    override fun createRequest() {

        createQueryParameters()
        super.createRequest()
    }

}