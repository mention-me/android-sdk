package com.mentionme.lib.Parameters

class MentionmeRequestParameters(partnerCode: String){

    /**
     * REQUIRED - Your partner code, used to link to your users and offers
     */
    public var partnerCode: String = partnerCode
    /**
     * REQUIRED - Situation - a string representing where in the application you are making this request
     */
    public var situation: String = ""
    /**
     * Locale code, ISO standard locale code (e.g. en_GB) for the locale you expect the content to be in
     */
    public var localeCode: String? = null
    /**
     * IP address of the customer connection. If you're making a request on behalf of a customer, pass their IP address here. If the customer will connect directly, leave this empty and we will retrieve this from their request.
     */
    public var ipAddress: String? = null
    /**
     * User Device Identifier should be a unique reference to this combination of app + user. We use this for de-duplication, reporting and anti-gaming. For example you could concatenate your CustomerId and a UniqueID representing the App Install and provide us with the hash. On Android the UniqueID could be an InstanceID or GUID. On iPhone the UniqueID should be generated by identifierForVendor.
     */
    public var userDeviceIdentifier: String? = null
    /**
     * Device type - your description of the device the user is using. We use this for performance and conversion optimisation.
     */
    public var deviceType: String? = null
    /**
     * Your application name. Used for reporting.
     */
    public var appName: String? = null
    /**
     * Your application version reference. Used for reporting and troubleshooting.
     */
    public var appVersion: String? = null
    /**
     * This is a digital signature for the request. If you wish to use this feature please discuss with us.
     */
    public var authenticationToken: String? = null
    /**
     * Variation - an index (0,1,2...) specifying which of the currently running AB tests to choose (if available)
     */
    public var variation: String? = null

}