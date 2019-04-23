package Fragments


import Utilities.OnGetOffLinkCustomerListener
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mentionme.lib.Objects.MentionmeContentCollectionLink
import com.mentionme.lib.Objects.MentionmeReferrer

import com.mentionme.mentionme.R
import kotlinx.android.synthetic.main.fragment_referrer_found.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ReferrerFoundFragment : Fragment() {

    var firstname: String = ""
    var referrer: MentionmeReferrer? = null
    var foundMultipleReferrers: Boolean? = null
    var links: ArrayList<MentionmeContentCollectionLink>? = null
    var inflatedView: View? = null
    var onGetOffLinkCustomerListener: OnGetOffLinkCustomerListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        if (inflatedView == null){
            this.inflatedView = inflater.inflate(R.layout.fragment_referrer_found, container, false)

            inflatedView!!.titleTextView.text = "Referrer Found"
            inflatedView!!.backButton.setOnClickListener {
                activity?.onBackPressed()
            }
            inflatedView!!.getOffButton.setOnClickListener {
                getOffAction()
            }

            updateUI(inflatedView!!)
        }

        return inflatedView
    }

    fun updateUI(view: View){

        val summary = referrer?.offer?.refereeReward?.summary ?: ""

        view.textView2.text = "Great, you'll get ${summary} thanks to ${firstname}"
        view.textView1.text = "Congratulations! Because ${firstname} referred you, you've got our BEST introductory offer... Simply enter your email address to get your voucher."
        view.getOffButton.setText("GET ${summary}")
    }

    fun getOffAction(){

        val email = inflatedView?.emailEditText?.text.toString()
        onGetOffLinkCustomerListener?.linkCustomer(email,firstname)
    }

    companion object {

        fun newInstance(referrer: MentionmeReferrer?, foundMultipleReferrers: Boolean?, links: ArrayList<MentionmeContentCollectionLink>?, firstname: String): ReferrerFoundFragment{
            val referrerFoundFragment = ReferrerFoundFragment()
            referrerFoundFragment.firstname = firstname
            referrerFoundFragment.links = links
            referrerFoundFragment.foundMultipleReferrers = foundMultipleReferrers
            referrerFoundFragment.referrer = referrer
            return referrerFoundFragment
        }
    }


}
