package com.mentionme.mentionme

import Adapters.ViewPageAdapter
import Fragments.*
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.mentionme.lib.Mentionme
import com.mentionme.lib.MentionmeConfig
import kotlinx.android.synthetic.main.activity_main.*
import Utilities.OnButtonClickedListener
import Utilities.OnFindReferrerByNameAndEmailListener
import Utilities.OnFindReferrerByNameListener
import Utilities.OnGetOffLinkCustomerListener
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.mentionme.lib.Objects.*
import com.mentionme.lib.Parameters.*
import com.mentionme.lib.Requests.*
import org.json.JSONObject
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    var FIRSTNAME = "Logan"
    var SURNAME = "Smith"
    var EMAIL = "logansmith813@mention-me.com"
    var PARTNER_CODE = "mmc5e7d476"//"PARTNER_CODE"
    var SALT = "ss57b8712a544b27b47a3633747e6638a5a781df1c810846974fcfe55ae7844658"

    var adapter: ViewPageAdapter? = null

    var offer: MentionmeOffer? = null
    var shareLinks: ArrayList<MentionmeShareLink>? = null
    var termsLinks: MentionmeTermsLinks? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                view_pager.setCurrentItem(0,false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                view_pager.setCurrentItem(1,false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                view_pager.setCurrentItem(2,false)

                mentionmeRequestGetDashboard()

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        configureUI()
        configureMentionMe()
        checkIfReferrerCanEnrol()
    }

    override fun onBackPressed() {
        if(!BackStackFragment.handleBackPressed(supportFragmentManager)){
            super.onBackPressed()
        }
    }

    fun configureUI(){

        //first tab initilizers
        val referrerFragment = ReferrerFragment()
        val firstTabNavFragment = HostFragment.newInstance(referrerFragment)
        referrerFragment.hideContentView()

        referrerFragment.onButtonClickedListener = object: OnButtonClickedListener{
            override fun buttonClicked() {

                val referrerEnrolledFragment = ReferrerEnrolledFragment.newInstance(offer,shareLinks,FIRSTNAME,SURNAME)
                firstTabNavFragment.replaceFragment(referrerEnrolledFragment,true)
            }
        }


        //second tab initilizers
        val refereeFragment = RefereeFragment()
        val secondTabNavFragment = HostFragment.newInstance(refereeFragment)

        refereeFragment.onFindReferrerByNameListener = object: OnFindReferrerByNameListener{
            override fun findReferrer(name: String) {
                mentionmeRequestFindReferrer(name)
            }
        }


        adapter = ViewPageAdapter(supportFragmentManager)
        adapter?.addFragment(firstTabNavFragment,"Referrer")
        adapter?.addFragment(secondTabNavFragment,"Referee")
        adapter?.addFragment(DashboardFragment(),"Dashboard")

        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = 2

    }

    fun updateUI(){

        val hostFragment = adapter?.getItem(0) as HostFragment
        val fragment = hostFragment.getVisibleFragment()
        if (fragment is ReferrerFragment){
            fragment.showContentView()
            fragment.updateUI(offer?.headline ?: "", offer?.descriptionOffer ?: "", termsLinks?.linkToTermsInLocale ?: "")
        }

    }

    fun updateUIWarning(message: String){
        val hostFragment = adapter?.getItem(0) as HostFragment
        val fragment = hostFragment.getVisibleFragment()
        if (fragment is ReferrerFragment){
            fragment.showWarningView()
            fragment.updateWarningUI(message)
        }
    }

    fun configureMentionMe(){


        val applicationInfo = applicationContext.getApplicationInfo()
        val stringId = applicationInfo.labelRes
        val appName =  if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else applicationContext.getString(stringId)

        val pinfo = packageManager.getPackageInfo(packageName, 0)
        val versionName = pinfo.versionName

        val requestParams = MentionmeRequestParameters(PARTNER_CODE)
        requestParams.deviceType = "android"
        requestParams.localeCode = "en_GB"
        requestParams.appName = appName
        requestParams.appVersion = versionName
        Mentionme.shared.requestParameters = requestParams
        Mentionme.shared.validationWarning = CustomValidationWarning()
        val config = MentionmeConfig(true)
        config.debugNetwork = true
        Mentionme.shared.config = config

    }

    fun mentionmeRequestGetDashboard(){

        //Next 4 lines merge email and salt and encrypt with SHA-256
        val bytes = "${EMAIL}${SALT}".toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val digestString = bytesToHex(digest)

        //Adding to requestParameters the authenticationToken encrypted with SHA-256
        Mentionme.shared.requestParameters?.authenticationToken = digestString

        //Calling the API 3. Dashboard
        Mentionme.shared.getReferrerDashboard(MentionmeDashboardRequest(MentionmeDashboardParameters(EMAIL)),"dashboard-screen",{ offer, shareLinks, termsLinks, stats, rewards ->

            runOnUiThread {
                updateDashboardUI(rewards,stats,offer,shareLinks)
            }

        },{
            println(it.errors)
            println(it.statusCode)
        },{
            println(it)
        })

    }

    private fun bytesToHex(hash: ByteArray): String {
        val hexString = StringBuffer()
        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString()
    }

    fun updateDashboardUI(dashboardRewards: ArrayList<MentionmeDashboardReward>?, referralStats: MentionmeReferralStats?, offer: MentionmeOffer?, shareLinks: ArrayList<MentionmeShareLink>?){

        val dashboardFragment = adapter?.getItem(2) as DashboardFragment
        dashboardFragment.updateUI(dashboardRewards, referralStats, offer, shareLinks,"${FIRSTNAME} ${SURNAME}")
    }

    fun mentionmeRequestFindReferrer(name: String){

        hideKeyboard()

        //Create Referrer Name Parameters and Request
        val request = MentionmeReferrerByNameRequest(MentionmeReferrerNameParameters(name))

        //Calling the API 4. Referrer By Name
        Mentionme.shared.findReferrerByName(request,"referrer-byname-screen",{ referrer, multipleReferrersFound, links ->

            println("-------Mention me referrer by name SUCCESS------")

            prepareToNavigateToResultScreen(name, referrer, multipleReferrersFound, links, null)

        },{ referrer, multipleReferrersFound, links, error ->

            println("----Mentionme-ERROR-----")
            println(error?.statusCode)
            println(error?.errors)
            println("---------------")

            prepareToNavigateToResultScreen(name, referrer, multipleReferrersFound, links, error?.statusCode)

        },{
            println("----NoResponse-ERROR-----")
            println(it)
            println("---------------")
        })

    }

    fun mentionmeRequestFindReferrerNameEmail(name: String, email: String){

        hideKeyboard()

        //Create Referrer Name Parameters and add email
        val params = MentionmeReferrerNameParameters(name)
        params.email = email
        //Create ReferrerByName Request
        val request = MentionmeReferrerByNameRequest(params)

        //Calling the API 4. Referrer By Name and email
        Mentionme.shared.findReferrerByName(request,"referrer-byname-screen",{ referrer, multipleReferrersFound, links ->

            println("-------Mention me referrer by name SUCCESS------")

            prepareToNavigateToResultScreen(name, referrer, multipleReferrersFound, links, null)

        },{ referrer, multipleReferrersFound, links, error ->

            println("----Mentionme-ERROR-----")
            println(error?.statusCode)
            println(error?.errors)
            println("---------------")

        },{
            println("----NoResponse-ERROR-----")
            println(it)
            println("---------------")
        })

    }

    fun prepareToNavigateToResultScreen(text: String, referrer: MentionmeReferrer?, foundMultipleReferrers: Boolean?, links: ArrayList<MentionmeContentCollectionLink>?, statusCode: Int?){

        var firstname = ""

        val result = text.split(" ")
        if (result.size > 0){
            firstname = result.first().capitalize()
        }

        statusCode?.let {

            runOnUiThread {
                val referrerNotFoundOrMultipleFragment = ReferrerNotFoundOrMultipleFragment.newInstance(referrer,links,firstname,foundMultipleReferrers)
                referrerNotFoundOrMultipleFragment.onFindReferrerByNameAndEmailListener = object: OnFindReferrerByNameAndEmailListener{
                    override fun findReferrer(name: String, email: String) {
                        mentionmeRequestFindReferrerNameEmail(name, email)
                    }
                }
                (adapter?.getItem(1) as HostFragment).replaceFragment(referrerNotFoundOrMultipleFragment,true)
            }

        } ?: run {

            runOnUiThread {
                val referrerFoundFragment = ReferrerFoundFragment.newInstance(referrer,foundMultipleReferrers,links,firstname)
                referrerFoundFragment.onGetOffLinkCustomerListener = object: OnGetOffLinkCustomerListener{
                    override fun linkCustomer(email: String,firstname: String) {
                        referrer?.let { referrer ->
                            mentionmeLinkCustomerRequest(email,firstname,referrer)
                        }
                    }
                }
                (adapter?.getItem(1) as HostFragment).replaceFragment(referrerFoundFragment, true)

            }

        }

    }

    fun mentionmeLinkCustomerRequest(email: String, firstname: String, referrer: MentionmeReferrer){

        //Create the Referrer Parameters
        val referrerParams = MentionmeReferrerParameters(referrer.referrerMentionMeIdentifier.toString(),referrer.referrerToken)
        //Create the Customer Parameters
        val customerParams = MentionmeCustomerParameters(email,"","")
        //Create the Referee Register Request
        val request = MentionmeRefereeRegisterRequest(referrerParams,customerParams)

        //Calling the API 5. Link new customer to referrer
        Mentionme.shared.linkNewCustomerToReferrer(request,"link-customer-screen",{ offer, reward, contentLink, status ->

            runOnUiThread {
                prepareToNavigateLinkCustomer(offer,reward,contentLink,status,email,firstname)
            }

        },{
            println(it.errors)
        },{
            println(it)
        })

    }

    fun prepareToNavigateLinkCustomer(offer: MentionmeOffer?, reward: MentionmeRefereeReward?, contentLink: MentionmeContentCollectionLink?, status: String?, email: String, firstname: String){

        val linkCustomerFragment = LinkCustomerFragment.newInstance(offer,reward,contentLink,status,email,firstname)
        (adapter?.getItem(1) as HostFragment).replaceFragment(linkCustomerFragment, true)

    }

    fun checkIfReferrerCanEnrol(){

        //Check if the enrollment can even happen before trying to enrol the referrer
        Mentionme.shared.entryPointForReferrerEnrollment(MentionmeReferrerEnrollmentRequest(),"app-check-enrol-referrer",{ url, defaultCallToActionString ->

            //if success then enrol referrer
            runOnUiThread {
                mentionmeEnrolReferrerRequest()
            }

        },{
            println(it)
            runOnUiThread {
                updateUIWarning(parseErrorMessage(it))
            }
        },{
            println(it)
        })

    }

    fun mentionmeEnrolReferrerRequest(){

        //Creating customer parameters.
        val customerParameters = MentionmeCustomerParameters(EMAIL,FIRSTNAME,SURNAME)

        //Creating customer request needed for referrer enrolment.
        val customerRequest = MentionmeCustomerRequest(customerParameters)

        //Calling the API 2. Enrol Referrer
        Mentionme.shared.enrolReferrer(customerRequest,"enrol-referrer-screen",{ offer, shareLinks, termsLinks ->

            println("-----SUCCESS-----")

            this.offer = offer
            this.shareLinks = shareLinks
            this.termsLinks = termsLinks

            runOnUiThread {
                updateUI()
            }

        },{
            println("----Mentionme-ERROR-----")
            println(it.statusCode)
            println(it.errors)
            println("---------------")
        },{
            println("----NoResponse-ERROR-----")
            println(it)
            println("---------------")
        })

    }

    fun parseErrorMessage(error: MentionmeError): String{

        if (error.errors.length() > 0){
            val jsonObject = error.errors.get(0) as JSONObject
            if (jsonObject.has("message")){
                val message = jsonObject.get("message").toString()
                return message
            }
        }

        return ""
    }

    fun hideKeyboard(){
        if (currentFocus != null){
            val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

}
