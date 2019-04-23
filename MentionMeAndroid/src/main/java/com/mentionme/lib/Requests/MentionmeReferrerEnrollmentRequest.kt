package com.mentionme.lib.Requests

import com.google.gson.JsonObject

class MentionmeReferrerEnrollmentRequest(): MentionmeRequest(){

    init {
        super.method = Method.POST
        super.urlSuffix = "referrer"
        super.urlEndpoint = "entry-point"
    }

    private fun createBodyParameters(){
        super.bodyParams = JsonObject()
    }

    override fun createRequest() {
        createBodyParameters()
        super.createRequest()
    }

}