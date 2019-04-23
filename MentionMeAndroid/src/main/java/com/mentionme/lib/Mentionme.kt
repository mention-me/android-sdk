package com.mentionme.lib

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mentionme.lib.Objects.*
import com.mentionme.lib.Parameters.MentionmeRequestParameters
import com.mentionme.lib.Requests.*
import okhttp3.*
import java.io.IOException

class Mentionme{

    ///Optional configuration class for setting demo mode or printing network debugging
    var config: MentionmeConfig? = null
    ///Information about the request
    var requestParameters: MentionmeRequestParameters? = null
    ///Class used for parameters validation
    public var validationWarning: MentionmeValidationWarning? = null

    private var okHttpClient: OkHttpClient? = null

    companion object {
        val shared = Mentionme()
    }

    init {
        okHttpClient = OkHttpClient.Builder().build()
    }

    /**
    Tell us that an order took place so that we can reward any appropriate referrer
     */
    fun recordOrder(mentionmeOrderRequest: MentionmeOrderRequest,
                    situation: String,
                    onSuccess:() -> Unit,
                    onFailure: (MentionmeError) -> Unit,
                    onNoResponse:(String) -> Unit){

        requestParameters?.let { requestParameters ->

            requestParameters.situation = situation

            validationWarning?.validate(requestParameters)
            validationWarning?.validate(mentionmeOrderRequest)

            mentionmeOrderRequest.createRequest()
            performRequest(mentionmeOrderRequest,requestParameters,{

                onSuccess()

            },{ _, error ->
                onFailure(error)
            },{
                onNoResponse(it)
            })

        } ?: run {
            onNoResponse("There are no request parameters set.")
        }

    }

    /**
    Tell us a customer's details to enrol them as a referrer and receive a referral offer for them to share
     */
    fun enrolReferrer(mentionmeCustomerRequest: MentionmeCustomerRequest,
                      situation: String,
                      onSuccess:(MentionmeOffer?, ArrayList<MentionmeShareLink>?, MentionmeTermsLinks?) -> Unit,
                      onFailure: (MentionmeError) -> Unit,
                      onNoResponse:(String) -> Unit){

        requestParameters?.let { requestParameters ->

            requestParameters.situation = situation

            validationWarning?.validate(requestParameters)
            validationWarning?.validate(mentionmeCustomerRequest)

            mentionmeCustomerRequest.createRequest()
            performRequest(mentionmeCustomerRequest, requestParameters,{

                MentionmeParser.getOffer(it){ offer, shareLinks, termsLinks ->
                    onSuccess(offer,shareLinks,termsLinks)
                }

            },{ _, error ->
                onFailure(error)
            },{
                onNoResponse(it)
            })

        } ?: run {
            onNoResponse("There are no request parameters set.")
        }

    }

    /**
    Get a referrer's dashboard (given a referrer identity, get their dashboard data)
     */
    fun getReferrerDashboard(mentionmeDashboardRequest: MentionmeDashboardRequest,
                             situation: String,
                             onSuccess: (MentionmeOffer?, ArrayList<MentionmeShareLink>?, MentionmeTermsLinks?, MentionmeReferralStats?, ArrayList<MentionmeDashboardReward>?) -> Unit,
                             onFailure: (MentionmeError) -> Unit,
                             onNoResponse: (String) -> Unit){

        requestParameters?.let { requestParameters ->

            requestParameters.situation = situation

            validationWarning?.validate(requestParameters)
            validationWarning?.validate(mentionmeDashboardRequest)

            mentionmeDashboardRequest.createRequest()
            performRequest(mentionmeDashboardRequest,requestParameters,{

                MentionmeParser.getDashboard(it){ offer, links, termsLinks, referralStats, dashboardRewards ->
                    onSuccess(offer, links, termsLinks, referralStats, dashboardRewards)
                }

            },{ _, error ->
                onFailure(error)
            },{
                onNoResponse(it)
            })

        } ?: run {
            onNoResponse("There are no request parameters set.")
        }

    }

    /**
    Search for a referrer to connect to a referee, using just their name
     */
    fun findReferrerByName(mentionmeReferrerByNameRequest: MentionmeReferrerByNameRequest,
                           situation: String,
                           onSuccess: (MentionmeReferrer?, Boolean?, ArrayList<MentionmeContentCollectionLink>?) -> Unit,
                           onFailure: (MentionmeReferrer?, Boolean?, ArrayList<MentionmeContentCollectionLink>?, MentionmeError?) -> Unit,
                           onNoResponse: (String) -> Unit){

        requestParameters?.let { requestParameters ->

            requestParameters.situation = situation

            validationWarning?.validate(requestParameters)
            validationWarning?.validate(mentionmeReferrerByNameRequest)

            mentionmeReferrerByNameRequest.createRequest()
            performRequest(mentionmeReferrerByNameRequest,requestParameters,{

                MentionmeParser.getReferrerByName(it){ referrer, foundMultipleReferrers, links ->
                    onSuccess(referrer,foundMultipleReferrers,links)
                }

            },{ jsonObject, error ->

                jsonObject?.let { jsonObject ->

                    MentionmeParser.getReferrerByName(jsonObject){ referrer, foundMultipleReferrers, links ->
                        onFailure(referrer,foundMultipleReferrers,links,error)
                    }

                }

            },{
                onNoResponse(it)
            })

        }?: run {
            onNoResponse("There are no request parameters set.")
        }

    }

    /**
    Post a referee's details to register them as a referee after successfully finding a referrer to link them to
     */
    fun linkNewCustomerToReferrer(mentionmeRefereeRegisterRequest: MentionmeRefereeRegisterRequest,
                                  situation: String,
                                  onSuccess: (MentionmeOffer?, MentionmeRefereeReward?, MentionmeContentCollectionLink?, String?) -> Unit,
                                  onFailure: (MentionmeError) -> Unit,
                                  onNoResponse: (String) -> Unit){

        requestParameters?.let { requestParameters ->

            requestParameters.situation = situation

            validationWarning?.validate(requestParameters)
            validationWarning?.validate(mentionmeRefereeRegisterRequest)

            mentionmeRefereeRegisterRequest.createRequest()
            performRequest(mentionmeRefereeRegisterRequest,requestParameters,{

                MentionmeParser.getRefereeRegister(it){ offer, refereeReward, contentCollectionLink, status ->
                    onSuccess(offer, refereeReward, contentCollectionLink, status)
                }

            },{ _, error ->
                onFailure(error)
            },{
                onNoResponse(it)
            })

        }?: run {
            onNoResponse("There are no request parameters set.")
        }

    }

    /**
    Request - Provide a customers' details so we can tell you if we could enrol them as a referrer. We'll provide the URL to the web-view for their journey
    */
    fun entryPointForReferrerEnrollment(mentionmeReferrerEnrollmentRequest: MentionmeReferrerEnrollmentRequest,
                                        situation: String,
                                        onSuccess: (String, String) -> Unit,
                                        onFailure: (MentionmeError) -> Unit,
                                        onNoResponse: (String) -> Unit){

        requestParameters?.let { requestParameters ->

            requestParameters.situation = situation

            validationWarning?.validate(requestParameters)
            validationWarning?.validate(mentionmeReferrerEnrollmentRequest)

            mentionmeReferrerEnrollmentRequest.createRequest()
            performRequest(mentionmeReferrerEnrollmentRequest,requestParameters,{

                MentionmeParser.getEnrollment(it){ url, defaultCallToAction ->
                    onSuccess(url, defaultCallToAction)
                }

            },{ _, error ->
                onFailure(error)
            },{
                onNoResponse(it)
            })

        }?: run {
            onNoResponse("There are no request parameters set.")
        }

    }

    fun performRequest(request: MentionmeRequest,
                       mentionmeRequestParameters: MentionmeRequestParameters,
                       onSuccess:(JsonObject) -> Unit,
                       onFailure: (JsonObject?, MentionmeError) -> Unit,
                       onNoResponse:(String) -> Unit){

        val httpUrl = HttpUrl.get(request.getUrl(mentionmeRequestParameters))
        val requestBuilder = okhttp3.Request.Builder().url(httpUrl)

        if (request.method == MentionmeRequest.Method.POST){
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), request.getBody(mentionmeRequestParameters).toString())
            requestBuilder.post(body)
        }

        val call = okHttpClient?.newCall(requestBuilder.build())

        call?.enqueue(object : Callback{

            override fun onResponse(call: Call?, response: Response?) {

                response?.let {

                    if (it.code() < 200 || it.code() > 299){

                        MentionmeParser.getError(it){ jsonObject, error ->
                            onFailure(jsonObject, error)
                        }
                    }else{
                        onSuccess(MentionmeParser.parse(response))
                    }

                } ?: run {
                    onNoResponse("There is no response")
                }

            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("onFailure")
                onNoResponse(e?.localizedMessage!!)
            }
        })

    }


}