package com.mentionme.lib

import com.mentionme.lib.Parameters.*
import com.mentionme.lib.Requests.*

open class MentionmeValidationWarning{

    init {

    }

    open fun reportWarning(warning: String){
        println(warning)
    }

    fun validate(mentionmeRequest: MentionmeRequest){

        if (mentionmeRequest is MentionmeCustomerRequest){
            validateCustomerRequest(mentionmeRequest)
        }else if (mentionmeRequest is MentionmeRefereeRegisterRequest){
            validateRefereeRequest(mentionmeRequest)
        }else if (mentionmeRequest is MentionmeReferrerByNameRequest){
            validateReferrerByNameRequest(mentionmeRequest)
        }else if (mentionmeRequest is MentionmeOrderRequest){
            validateOrderRequest(mentionmeRequest)
        }else if (mentionmeRequest is MentionmeDashboardRequest){
            validateDashboardRequest(mentionmeRequest)
        }

    }

    fun validate(requestParameters: MentionmeRequestParameters){

        if (requestParameters.partnerCode.count() > 50){
            reportWarning("Request Parameter partnerCode is over 50 characters")
        }
        if (requestParameters.situation.count() > 50){
            reportWarning("Request Parameter situation is over 50 characters")
        }

        val pattern = "^[a-zA-Z0-9_\\- ]+$"
        val regex = Regex(pattern)
        if (!regex.matches(requestParameters.situation)){
            reportWarning("Request Parameter situation contains an invalid character")
        }

        requestParameters.ipAddress?.let {
            if (it.count() > 20){
                reportWarning("Request Parameter ipAddress is over 20 characters")
            }
        }

        requestParameters.variation?.let {
            if (it.toIntOrNull() == null){
                reportWarning("Request Parameter variation is not Integer")
            }
        }

        requestParameters.localeCode?.let {
            if (it.count() > 5){
                reportWarning("Request Parameter localeCode is over 5 characters")
            }
        }

    }

    private fun validateCustomerRequest(request: MentionmeCustomerRequest){

        request.mentionmeCustomerParameters?.let {
            validateCustomerParameters(it)
        }

    }

    private fun validateRefereeRequest(request: MentionmeRefereeRegisterRequest){

        request.mentionmeCustomerParameters?.let {
            validateCustomerParameters(it)
        }

    }

    private fun validateReferrerByNameRequest(request: MentionmeReferrerByNameRequest){

        request.mentionmeReferrerNameParameters?.let {
            validateReferrerByNameParameters(it)
        }

    }

    private fun validateOrderRequest(request: MentionmeOrderRequest){

        request.mentionmeOrderParameters?.let{
            validateOrderParameters(it)
        }
        request.mentionmeCustomerParameters?.let{
            validateCustomerParameters(it)
        }

    }

    private fun validateDashboardRequest(request: MentionmeDashboardRequest){

        request.mentionmeDashboardParameters?.let {
            validateDashboardParameters(it)
        }
    }

    private fun validateDashboardParameters(dashboardParameters: MentionmeDashboardParameters){

        if (dashboardParameters.emailAddress.count() > 255){
            reportWarning("Dashboard Parameter emailAddress is over 255 characters")
        }
        dashboardParameters.uniqueCustomerIdentifier?.let {
            if (it.count() > 255){
                reportWarning("Dashboard Parameter uniqueCustomerIdentifier is over 255 characters")
            }
        }
    }


    private fun validateReferrerByNameParameters(referrerByNameParameters: MentionmeReferrerNameParameters){

        if (referrerByNameParameters.name.count() > 255){
            reportWarning("Referrer Parameter name is over 255 characters")
        }
        referrerByNameParameters.email?.let {
            if (it.count() > 255){
                reportWarning("Referrer Parameter email is over 255 characters")
            }
        }

    }

    private fun validateOrderParameters(orderParameters: MentionmeOrderParameters){

        if (orderParameters.orderIdentifier.count() > 50){
            reportWarning("Order Parameter orderIdentifier is over 50 characters")
        }
        orderParameters.couponCode?.let {
            if (it.count() > 255){
                reportWarning("Order Parameter couponCode is over 255 characters")
            }
        }
        if (orderParameters.currencyCode.count() != 3){
            reportWarning("Order Parameter currencyCode is not 3 characters")
        }

    }

    private fun validateCustomerParameters(customerParameters: MentionmeCustomerParameters){

        customerParameters.title?.let {
            if (it.count() > 20){
                reportWarning("Customer Parameter title is over 20 characters")
            }
        }
        if (customerParameters.firstname.count() > 255){
            reportWarning("Customer Parameter firstname is over 255 characters")
        }
        if (customerParameters.surname.count() > 255){
            reportWarning("Customer Parameter surname is over 255 characters")
        }
        if (customerParameters.emailAddress.count() > 255){
            reportWarning("Customer Parameter email is over 255 characters")
        }
        customerParameters.uniqueIdentifier?.let {
            if (it.count() > 255){
                reportWarning("Customer Parameter uniqueIdentifier is over 255 characters")
            }
        }
        customerParameters.segment?.let {
            if (it.count() > 50){
                reportWarning("Customer Parameter segment is over 50 characters")
            }

            val pattern = "^[a-zA-Z0-9_\\-\\| \\*%&,.;':\"\\[\\]\\$£]+$"
            val regex = Regex(pattern)
            if (!regex.matches(it)){
                reportWarning("Customer Parameter segment contains an invalid character")
            }


        }

    }






}