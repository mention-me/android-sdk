package com.mentionme.lib.Requests

import com.google.gson.JsonObject
import com.mentionme.lib.Mentionme
import com.mentionme.lib.Parameters.MentionmeRequestParameters
import okhttp3.HttpUrl
import java.net.URL
import java.util.*

open class MentionmeRequest(){

    enum class Method {
        GET, POST
    }

    var baseURLUAT = "https://uat.mention-me.com"
    var baseURLDemo = "https://demo.mention-me.com"
    var baseURL = "https://mention-me.com"
    var urlSuffix = ""
    var urlEndpoint = ""
    var method = Method.GET
    var queryParams: HashMap<String, Any>? = null
    var bodyParams: JsonObject? = null

    init {

    }

    open fun createRequest(){

    }

    fun getUrl(mentionmeRequestParameters: MentionmeRequestParameters): URL {

        var urlString = "${baseURL}/api/${urlEndpoint}/v1/${urlSuffix}"

        Mentionme.shared.config?.let {
            if (it.demo){
                urlString = "${baseURLDemo}/api/${urlEndpoint}/v1/${urlSuffix}"
            }else if (it.uat){
                urlString = "${baseURLUAT}/api/${urlEndpoint}/v1/${urlSuffix}"
            }
        }

        val urlBuilder = HttpUrl.parse(urlString).newBuilder()

        queryParams?.let {
            urlString += "?"

            it.put("request",requestParams(mentionmeRequestParameters))

            for (entry in it.entries){

                if (entry.value is String){
                    urlBuilder.addQueryParameter(entry.key,entry.value.toString())
                }else{

                    if (entry.value is JsonObject){
                        val jsonObject = entry.value as JsonObject
                        for (ent in jsonObject.entrySet()){
                            urlBuilder.addQueryParameter("${entry.key}[${ent.key}]",ent.value.asString)
                        }
                    }

                }

            }

        }

        Mentionme.shared.config?.let {
            if (it.debugNetwork){
                println(urlBuilder.build().url().toString())
            }
        }

        return urlBuilder.build().url()
    }

    fun getBody(mentionmeRequestParameters: MentionmeRequestParameters): JsonObject?{

        if (bodyParams == null){ return null}
        bodyParams?.add("request",requestParams(mentionmeRequestParameters))
        return bodyParams
    }

    fun requestParams(mentionmeRequestParameters: MentionmeRequestParameters): JsonObject{

        val params = JsonObject()

        params.addProperty("partnerCode",mentionmeRequestParameters.partnerCode)
        params.addProperty("situation",mentionmeRequestParameters.situation)
        if (mentionmeRequestParameters.localeCode != null && mentionmeRequestParameters.localeCode != "null"){
            params.addProperty("localeCode",mentionmeRequestParameters.localeCode!!)
        }
        if (mentionmeRequestParameters.ipAddress != null && mentionmeRequestParameters.ipAddress != ""){
            params.addProperty("ipAddress",mentionmeRequestParameters.ipAddress!!)
        }
        if (mentionmeRequestParameters.userDeviceIdentifier != null && mentionmeRequestParameters.userDeviceIdentifier != ""){
            params.addProperty("userDeviceIdentifier", mentionmeRequestParameters.userDeviceIdentifier)
        }
        if (mentionmeRequestParameters.deviceType != null && mentionmeRequestParameters.deviceType != ""){
            params.addProperty("deviceType", mentionmeRequestParameters.deviceType)
        }
        if (mentionmeRequestParameters.appName != null && mentionmeRequestParameters.appName != ""){
            params.addProperty("appName", mentionmeRequestParameters.appName)
        }
        if (mentionmeRequestParameters.appVersion != null && mentionmeRequestParameters.appVersion != ""){
            params.addProperty("appVersion", mentionmeRequestParameters.appVersion)
        }
        if (mentionmeRequestParameters.authenticationToken != null && mentionmeRequestParameters.authenticationToken != ""){
            params.addProperty("authenticationToken", mentionmeRequestParameters.authenticationToken)
        }
        if (mentionmeRequestParameters.variation != null && mentionmeRequestParameters.variation != "null"){
            params.addProperty("variation", mentionmeRequestParameters.variation)
        }

        return params
    }

}