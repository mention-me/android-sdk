package Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mentionme.lib.Objects.MentionmeContentCollectionLink
import com.mentionme.lib.Objects.MentionmeOffer
import com.mentionme.lib.Objects.MentionmeRefereeReward

import com.mentionme.mentionme.R
import kotlinx.android.synthetic.main.fragment_link_customer.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LinkCustomerFragment : Fragment() {

    var offer: MentionmeOffer? = null
    var refereeReward: MentionmeRefereeReward? = null
    var content: MentionmeContentCollectionLink? = null
    var status: String? = null
    var email: String = ""
    var firstname: String = ""

    var inflatedView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        if (inflatedView == null){
            inflatedView = inflater.inflate(R.layout.fragment_link_customer, container, false)

            inflatedView!!.titleTextView.text = "Coupon"
            inflatedView!!.backButton.setOnClickListener {
                activity?.onBackPressed()
            }

            updateUI(inflatedView!!)
        }

        return inflatedView
    }

    fun updateUI(view: View){

        status?.let {

            if (it == "Success"){

                view.textView2.text = "This reward is valid for 7 days. We've also emailed this to you."
                view.textView1.text = refereeReward?.descriptionRefereeReward ?: ""
                view.codeTextView.text = refereeReward?.couponCode ?: ""
                view.offerTextView.text = offer?.refereeReward?.summary ?: ""
                view.emailTextView.text = "For use by ${email} only"

            }else if (it == "OfferAlreadyRedeemed"){

                view.couponView.visibility = View.GONE
                view.continueShoppingButton.visibility = View.GONE

                view.textView2.text = "You have already claimed this offer after ${firstname} introduced you. You can only claim it from one friend."
                view.textView1.text = "You've already claimed this offer."

            }else if (it == "OfferAlreadyFulfilled"){

                view.textView2.text = "This is the same offer you've already seen. You can only use it once but in case you haven't used it yet, here it is again."
                view.textView1.text = refereeReward?.descriptionRefereeReward ?: ""
                view.codeTextView.text = refereeReward?.couponCode ?: ""
                view.offerTextView.text = offer?.refereeReward?.summary ?: ""
                view.emailTextView.text = "For use by ${email} only"

            }else if (it == "ReferringSelf"){

                view.couponView.visibility = View.GONE
                view.continueShoppingButton.visibility = View.GONE
                view.textView1.text = "Sorry ${firstname}"
                var token = ""
                content?.resource?.let {
                    for (content in it) {
                        if (content.token.count() > token.count()){
                            token = content.token
                        }
                    }
                }
                view.textView2.text = token

            }else if (it == "AlreadyCustomer"){

                view.couponView.visibility = View.GONE
                view.continueShoppingButton.visibility = View.GONE

                var token = ""
                content?.resource?.let {
                    for (content in it) {
                        if (content.token.count() > token.count()){
                            token = content.token
                        }
                    }
                }
                view.textView2.text = token
                view.textView1.text = offer?.headline ?: ""
            }

        }

    }

    companion object {
        fun newInstance(offer: MentionmeOffer?, reward: MentionmeRefereeReward?, contentLink: MentionmeContentCollectionLink?, status: String?, email: String, firstname: String): LinkCustomerFragment{
            val fragment = LinkCustomerFragment()
            fragment.offer = offer
            fragment.refereeReward = reward
            fragment.content = contentLink
            fragment.status = status
            fragment.email = email
            fragment.firstname = firstname
            return fragment
        }
    }

}
