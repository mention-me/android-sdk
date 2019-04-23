package com.mentionme.lib

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.mentionme.lib.Objects.*
import jdk.nashorn.internal.parser.JSONParser
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.io.Reader
import java.io.StringReader
import javax.print.DocFlavor.STRING



class MentionmeParser{

    companion object {

        fun parse(response: Response): JsonObject{
            return JsonParser().parse(response.body().string()).asJsonObject
        }

        @Throws(IOException::class)
        fun getError(response: Response, completion: (JsonObject?,MentionmeError) -> Unit){

            try {

                val body = response.body().string()

                val jsonReader = JsonReader(StringReader(body))
                jsonReader.isLenient = true

                val jsonElement = JsonParser().parse(jsonReader)
                if (jsonElement is JsonObject){

                    val jsonObject = jsonElement.asJsonObject

                    Mentionme.shared.config?.let {
                        if (it.debugNetwork){
                            println(jsonObject)
                        }
                    }

                    if (jsonObject.has("errors") && jsonObject.get("errors").isJsonArray){
                        val errors = jsonObject.getAsJsonArray("errors")
                        val code = response.code()
                        val error = MentionmeError(errors,code)
                        completion(null,error)
                    }else{
                        completion(jsonObject,MentionmeError(JsonArray(),response.code()))
                    }

                }else{

                    Mentionme.shared.config?.let {
                        if (it.debugNetwork){
                            println("Response is not valid json data")
                        }
                    }

                    completion(null, MentionmeError(JsonArray(),response.code()))

                }

            } catch (e: IOException){

                Mentionme.shared.config?.let {
                    if (it.debugNetwork){
                        println("There was a JsonSyntaxException with the response data")
                        println(e)
                    }
                }
                completion(null, MentionmeError(JsonArray(),response.code()))
            }

        }

        fun getOffer(jsonObject: JsonObject, onSuccess:(MentionmeOffer?, ArrayList<MentionmeShareLink>?, MentionmeTermsLinks?) -> Unit){

            Mentionme.shared.config?.let {
                if (it.debugNetwork){
                    println(jsonObject)
                }
            }

            var offer: MentionmeOffer? = null
            var shareLinks: ArrayList<MentionmeShareLink>? = null
            var termsLinks: MentionmeTermsLinks? = null

            if (jsonObject.has("offer")){
                offer = MentionmeOffer(jsonObject.getAsJsonObject("offer"))
            }
            if (jsonObject.has("shareLinks")){
                shareLinks = arrayListOf()
                for (json in jsonObject.getAsJsonArray("shareLinks")){
                    shareLinks.add(MentionmeShareLink(json.asJsonObject))
                }
            }
            if (jsonObject.has("termsLinks")){
                termsLinks = MentionmeTermsLinks(jsonObject.getAsJsonObject("termsLinks"))
            }

            onSuccess(offer,shareLinks,termsLinks)

        }

        fun getDashboard(jsonObject: JsonObject, onSuccess: (MentionmeOffer?, ArrayList<MentionmeShareLink>?, MentionmeTermsLinks?, MentionmeReferralStats?, ArrayList<MentionmeDashboardReward>?) -> Unit){

            Mentionme.shared.config?.let {
                if (it.debugNetwork){
                    println(jsonObject)
                }
            }

            var offer: MentionmeOffer? = null
            var shareLinks: ArrayList<MentionmeShareLink>? = null
            var termsLinks: MentionmeTermsLinks? = null
            var referralStats: MentionmeReferralStats? = null
            var dashboardRewards: ArrayList<MentionmeDashboardReward>? = null

            if (jsonObject.has("offer") && jsonObject.get("offer").isJsonObject){
                offer = MentionmeOffer(jsonObject.getAsJsonObject("offer"))
            }
            if (jsonObject.has("shareLinks") && jsonObject.get("shareLinks").isJsonArray){
                shareLinks = arrayListOf()
                for (json in jsonObject.getAsJsonArray("shareLinks")){
                    shareLinks.add(MentionmeShareLink(json.asJsonObject))
                }
            }
            if (jsonObject.has("termsLinks") && jsonObject.get("termsLinks").isJsonObject){
                termsLinks = MentionmeTermsLinks(jsonObject.getAsJsonObject("termsLinks"))
            }
            if (jsonObject.has("referralStats") && jsonObject.get("referralStats").isJsonObject){
                referralStats = MentionmeReferralStats(jsonObject.get("referralStats").asJsonObject)
            }
            if (jsonObject.has("referralRewards") && jsonObject.get("referralRewards").isJsonArray){
                dashboardRewards = arrayListOf()
                for (json in jsonObject.getAsJsonArray("referralRewards")){
                    dashboardRewards.add(MentionmeDashboardReward(json.asJsonObject))
                }
            }

            onSuccess(offer, shareLinks, termsLinks, referralStats, dashboardRewards)

        }

        fun getReferrerByName(jsonObject: JsonObject, onSuccess:(MentionmeReferrer?, Boolean?, ArrayList<MentionmeContentCollectionLink>?) -> Unit){

            Mentionme.shared.config?.let {
                if (it.debugNetwork){
                    println(jsonObject)
                }
            }

            var referrer: MentionmeReferrer? = null
            var foundMultipleReferrers: Boolean? = null
            var links: ArrayList<MentionmeContentCollectionLink>? = null

            if (jsonObject.has("referrer") && jsonObject.get("referrer").isJsonObject){
                referrer = MentionmeReferrer(jsonObject.getAsJsonObject("referrer"))
            }
            if (jsonObject.has("foundMultipleReferrers")){
                foundMultipleReferrers = jsonObject.get("foundMultipleReferrers").asBoolean
            }
            if (jsonObject.has("links")){
                links = arrayListOf()
                for (json in jsonObject.getAsJsonArray("links")){
                    links.add(MentionmeContentCollectionLink(json.asJsonObject))
                }
            }

            onSuccess(referrer,foundMultipleReferrers,links)
        }

        fun getRefereeRegister(jsonObject: JsonObject, onSuccess:(MentionmeOffer?, MentionmeRefereeReward?, MentionmeContentCollectionLink?, String?) -> Unit){

            Mentionme.shared.config?.let {
                if (it.debugNetwork){
                    println(jsonObject)
                }
            }

            var offer: MentionmeOffer? = null
            var refereeReward: MentionmeRefereeReward? = null
            var contentCollectionLink: MentionmeContentCollectionLink? = null
            var status: String? = null

            if (jsonObject.has("offer") && jsonObject.get("offer").isJsonObject){
                offer = MentionmeOffer(jsonObject.get("offer").asJsonObject)
            }
            if (jsonObject.has("refereeReward") && jsonObject.get("refereeReward").isJsonObject){
                refereeReward = MentionmeRefereeReward(jsonObject.get("refereeReward").asJsonObject)
            }
            if (jsonObject.has("content") && jsonObject.get("content").asJsonObject.isJsonObject){
                contentCollectionLink = MentionmeContentCollectionLink(jsonObject.get("content").asJsonObject)
            }
            if (jsonObject.has("status")){
                status = jsonObject.get("status").asString
            }

            onSuccess(offer,refereeReward,contentCollectionLink,status)

        }

        fun getEnrollment(jsonObject: JsonObject, onSuccess:(String, String) -> Unit){

            Mentionme.shared.config?.let {
                if (it.debugNetwork){
                    println(jsonObject)
                }
            }

            var url: String = ""
            var defaultCallToAction: String = ""

            if (jsonObject.has("url")){
                url = jsonObject.get("url").asString
            }
            if (jsonObject.has("defaultCallToAction")){
                defaultCallToAction = jsonObject.get("defaultCallToAction").asString
            }

            onSuccess(url,defaultCallToAction)

        }


    }

}