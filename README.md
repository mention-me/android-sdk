## README

Supercharge your customer growth with referral marketing through Mention Me

* Refer a friend platform tailored to your brand
* AB testing to optimise your programme
* Uniquely captures word of mouth sharing
* Best practice insight from our Client Success Team

This is an SDK for clients of Mention Me to integrate referral into their iOS apps.
If you're interested in becoming a client, [please contact us first](https://blog.mention-me.com/contact-us).

## Installation

1. Download repository and unzip
2. In android studio File -> New -> Import Module -> Select the path of the lib
3. Add grandle dependency. 

```
implementation project(path: ':MentionMeAndroid')
```

## Documentation

It's worth first reading about [integrating with Mention Me in general](https://demo.mention-me.com/api-demo/v2/generic/apps/instructions/overview). And the API reference: [Mention Me API](https://demo.mention-me.com/api/consumer/v1/doc)

You will need to setup the Request Parameters with your partnerCode.
```
Mentionme.shared.requestParameters = MentionmeRequestParameters(PARTNER_CODE)
```

You should always use the demo mode in the SDK when developing and testing. The demo platform is a like-production version of Mention Me and will be set up with your account and has demo campaigns which will be returned when you make SDK requests.

To enable demo API mode and the debug network log simply include the following:

```
val config = MentionmeConfig(true)
config.debugNetwork = true
Mentionme.shared.config = config
```
Setting it to false or not including it at all, will default to non-demo mode.

You can send the demo system test/mocked customer data without risk of real customers being emailed, coupons being given out or cash/rewards changing hands. We can give you logins to the client dashboard on the demo platform at your request so you can review the state of the integration.

The offers and customer experience at each touchpoint can be disabled at any time from the Mention Me platform without requiring any development involvement so its possible to go live with your SDK app integration before any campaign is designed to start and it can start, change and finish without any further development involvement.


Optionally you can create your own custom validation warning class and override the reportWarning function for analytics purposes.
```
Mentionme.shared.validationWarning = CustomValidationWarning()
```

### There are 4 things we'll need to do in any typical customer journey (on web or in app):

 - Promote referral to those customers who are happy to share (and let them share)
 
 - Track successful orders or sign ups of new customers who may have been referred 

 - Allow "name sharing" to occur in the app for new customers who come to the app having been referred by word of mouth.

 - Show existing customers who are referrers a dashboard so they can track their shares and rewards

### 1. Enrol Referrer
Tell us a customer's details to enrol them as a referrer and receive a referral offer for them to share, including individual share methods and unique share tracking URLs to include in any native shares.

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

### Each promotional touchpoint in your app should have a specific "situation" name e.g. app-main-menu or post-purchase-success 

These different situation parameters allow us to control each touchpoint independently from the Mention Me platform meaning you can safely build new touchpoints into your app but have them turned off until fully tested and their use and variety controlled by the team managing the referral programme.

As an example, you can check if the referrer enrollment touchpoint is enabled for a particular situation (e.g. app-postpurchase-success) before enrolling someone with the following:

```
Mentionme.shared.entryPointForReferrerEnrollment(MentionmeReferrerEnrollmentRequest(),"app-postpurchase-success",{ url, defaultCallToActionString ->

    //if success then enrol referrer
    runOnUiThread {  }

},{
    //If failure display appropriate message in ui thread
    runOnUiThread {  }
},{
    println(it)
})
```

This endpoint also returns a "defaultCallToActionString" which is content which may be dynamic and can be used to set the content of a button or link. This allows the marketing team to AB test the content of such buttons (e.g. Get £20 when you refer vs Get 20% off when you refer) without the app developers needing to coordinate changes to copy.


### 2. Record Order
Tell us that an order (or customer sign up) took place so that we can reward any appropriate referrer:

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

### 3a. Find referrer by name
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

### 3b. Link new customer to referrer
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

### 4. Get referrer dashboard
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

If you'd like help designing your integration or have any questions, please reach out to your Onboarding Manager at Mention Me or email help@mention-me.com.
