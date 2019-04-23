package com.mentionme.lib.Parameters

class MentionmeDashboardParameters(emailAddress: String){

    /**
     * REQUIRED - Email address of the referrer whose dashboard you require
     */
    public var emailAddress: String = emailAddress
    /**
     * Customer Id - your unique identifier for this customer
     */
    public var uniqueCustomerIdentifier: String? = null

}