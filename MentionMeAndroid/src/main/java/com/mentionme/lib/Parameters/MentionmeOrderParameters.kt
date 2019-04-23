package com.mentionme.lib.Parameters

class MentionmeOrderParameters(orderIdentifier: String, total: String, currencyCode: String, dateString: String){

    /**
     * REQUIRED - Your Order Identifier for the transaction that has taken place
     */
    public var orderIdentifier: String = orderIdentifier
    /**
     * REQUIRED - Order total, excluding tax and shipping in the currency specified by currencyCode
     */
    public var total: String = total
    /**
     * REQUIRED - 3 letter currency code for the currency in which the transaction took place
     */
    public var currencyCode: String = currencyCode
    /**
     * REQUIRED - The date on which the transaction took place (typically the current date/time). Use ISO8601 format (e.g. 2016-11-30T17:52:50Z)
     */
    public var dateString: String = dateString
    /**
     * If a coupon was used in the transaction, the coupon code the consumer used
     */
    public var couponCode: String? = null

}