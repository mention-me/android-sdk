## README

Supercharge your customer growth with referral marketing through Mention Me

* Refer a friend platform tailored to your brand
* AB testing to optimise your programme
* Uniquely captures word of mouth sharing
* Best practice insight from our Client Success Team

## Installation

1. Download repository and unzip
2. In android studio File -> New -> Import Module -> Select the path of the lib
3. Add grandle dependency. 

```
implementation project(path: ':MentionMeAndroid')
```

## Documentation

[Mention Me API](https://demo.mention-me.com/api/consumer/v1/doc)

To enable demo API mode and the debug network log simply include the following:

```
val config = MentionmeConfig(true)
config.debugNetwork = true
Mentionme.shared.config = config
```
Setting it to false or not including it at all, will default to non-demo mode.

You will need to setup the Request Parameters with your partnerCode.
```
Mentionme.shared.requestParameters = MentionmeRequestParameters(PARTNER_CODE)
```

Optionally you can create your own custom validation warning class and override the reportWarning function for analytics purposes.
```
Mentionme.shared.validationWarning = CustomValidationWarning()
```

### You can check if the referrer enrollment works before enrolling someone with the following:
```
Mentionme.shared.entryPointForReferrerEnrollment(MentionmeReferrerEnrollmentRequest(),"app-check-enrol-referrer",{ url, defaultCallToActionString ->

    //if success then enrol referrer
    runOnUiThread {  }

},{
    //If failure display appropriate message in ui thread
    runOnUiThread {  }
},{
    println(it)
})
```

### 1. Record Order
Tell us that an order took place so that we can reward any appropriate referrer
```
val orderParms = MentionmeOrderParameters(orderIdentifier,total,currencyCode,dateString)
val customerParams = MentionmeCustomerParameters(EMAIL,FIRSTNAME,SURNAME)
val request = MentionmeOrderRequest(orderParms,customerParams)

Mentionme.shared.recordOrder(request,"app-record-order-screen",{

    //success
    runOnUiThread {  }

},{
    println(it.errors)
},{
    println(it)
})
```

### 2. Enrol Referrer
Tell us a customer's details to enrol them as a referrer and receive a referral offer for them to share
```
val customerParameters = MentionmeCustomerParameters(EMAIL,FIRSTNAME,SURNAME)
val customerRequest = MentionmeCustomerRequest(customerParameters)

Mentionme.shared.enrolReferrer(customerRequest,"enrol-referrer-screen",{ offer, shareLinks, termsLinks ->

    runOnUiThread {
        //update UI
    }

},{
    println(it.errors)
},{
    println(it)
})
```

### 3. Get referrer dashboard
Get a referrer's dashboard (given a referrer identity, get their dashboard data)
```
//The digestString is the email followed by the salt encrypted with SHA-256
Mentionme.shared.requestParameters?.authenticationToken = digestString

Mentionme.shared.getReferrerDashboard(MentionmeDashboardRequest(MentionmeDashboardParameters(EMAIL)),"dashboard-screen",{ offer, shareLinks, termsLinks, stats, rewards ->

    //update UI in ui thread
    runOnUiThread {  }

},{
    println(it.errors)
},{
    println(it)
})
```

### 4. Find referrer by name
Search for a referrer to connect to a referee, using just their name
```
val request = MentionmeReferrerByNameRequest(MentionmeReferrerNameParameters(name))

Mentionme.shared.findReferrerByName(request,"referrer-byname-screen",{ referrer, multipleReferrersFound, links ->

    //update UI or navigate to another screen
    runOnUiThread {  }

},{ referrer, multipleReferrersFound, links, error ->
    println(error?.errors)
},{
    println(it)
})
```

### 5. Link new customer to referrer
Post a referee's details to register them as a referee after successfully finding a referrer to link them to
```
val referrerParams = MentionmeReferrerParameters(referrer.referrerMentionMeIdentifier.toString(),referrer.referrerToken)
val customerParams = MentionmeCustomerParameters(email,firstname,surname)
val request = MentionmeRefereeRegisterRequest(referrerParams,customerParams)

Mentionme.shared.linkNewCustomerToReferrer(request,"link-customer-screen",{ offer, reward, contentLink, status ->
    
    //update UI
    runOnUiThread {  }

},{
    println(error?.errors)
},{
    println(it)
})
```
