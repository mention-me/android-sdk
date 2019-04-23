package Fragments


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mentionme.lib.Objects.MentionmeDashboardReward
import com.mentionme.lib.Objects.MentionmeOffer
import com.mentionme.lib.Objects.MentionmeReferralStats
import com.mentionme.lib.Objects.MentionmeShareLink

import com.mentionme.mentionme.R
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DashboardFragment : Fragment() {

    var dashboardRewards: ArrayList<MentionmeDashboardReward>? = null
    var referralStats: MentionmeReferralStats? = null
    var offer: MentionmeOffer? = null
    var shareLinks: ArrayList<MentionmeShareLink>? = null

    var inflatedView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        if (inflatedView == null){
            inflatedView = inflater.inflate(R.layout.fragment_dashboard, container, false)


            inflatedView!!.titleTextView.text = "Dashboard"

            inflatedView!!.shareButton.setOnClickListener {
                shareAction()
            }

            inflatedView!!.shareByEmailButton.setOnClickListener { shareAction() }
            inflatedView!!.shareByFacebookButton.setOnClickListener { shareAction() }
            inflatedView!!.shareByLinkButton.setOnClickListener { shareAction() }
            inflatedView!!.shareByMessengerButton.setOnClickListener { shareAction() }

        }

        return inflatedView
    }

    fun updateUI(dashboardRewards: ArrayList<MentionmeDashboardReward>?, referralStats: MentionmeReferralStats?, offer: MentionmeOffer?, shareLinks: ArrayList<MentionmeShareLink>?, fullname: String){

        this.dashboardRewards = dashboardRewards
        this.referralStats = referralStats
        this.offer = offer
        this.shareLinks = shareLinks

        inflatedView?.referralsTextView?.text = referralStats?.successfulReferrals?.toString() ?: "0"
        inflatedView?.invitesTextView?.text = referralStats?.invitations?.toString() ?: "0"
        inflatedView?.clicksOnInvitesTextView?.text = referralStats?.clicksOnInvites?.toString() ?: "0"

        inflatedView?.nameSurnameTextView?.text = fullname


        dashboardRewards?.let {
            it.forEach { reward ->

                println(reward.forReferring)
                println(reward.summary)
                println(reward.status)

                val tv = TextView(context)
                tv.text = "${reward.forReferring} - ${reward.summary}\n${reward.status}"
                tv.setTextColor(Color.BLACK)

                inflatedView?.rewardsView?.addView(tv)

            }
        }



    }

    private fun shareAction(){

        var url = ""

        shareLinks?.let {
            if (it.size > 0){
                url = it[0].url
            }
        }

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(shareIntent,"Share"))
    }

    companion object {
        fun newInstance(dashboardRewards: ArrayList<MentionmeDashboardReward>?, referralStats: MentionmeReferralStats?, offer: MentionmeOffer?, shareLinks: ArrayList<MentionmeShareLink>?): DashboardFragment{
            val dashboardFragment = DashboardFragment()
            dashboardFragment.dashboardRewards = dashboardRewards
            dashboardFragment.referralStats = referralStats
            dashboardFragment.offer = offer
            dashboardFragment.shareLinks = shareLinks
            return dashboardFragment
        }
    }


}
