package com.mentionme.lib.Parameters

class MentionmeCustomerParameters(emailAddress: String, firstname: String, surname: String){

    /**
     * REQUIRED - Customer email address
     */
    public var emailAddress: String = emailAddress
    /**
     * Customer title such as Mr, Miss, Dr or Sir
     */
    public var title: String? = null
    /**
     * REQUIRED - Customer firstname
     */
    public var firstname: String = firstname
    /**
     * REQUIRED - Customer surname
     */
    public var surname: String = surname
    /**
     * Your unique identifier for this customer e.g. CustomerId
     */
    public var uniqueIdentifier: String? = null
    /**
     * Customer segment, a string containing segment data about this customer, e.g. vip or employee. You can concatenate multiple segments together if you wish using hyphens.
     */
    public var segment: String? = null


}