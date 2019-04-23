package com.mentionme.lib.Parameters

class MentionmeReferrerNameParameters(name: String){

    /**
     * Name of a referrer to search for (entered by the new customer)
     */
    public var name: String = name
    /**
     * Optionally ask the new customer to qualify the name with an email address belonging to the referrer. We typically ask for a Name first and then if no matches found, offer Name + Email address.
     */
    public var email: String? = null

}