package Fragments


import Utilities.OnFindReferrerByNameAndEmailListener
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mentionme.lib.Objects.MentionmeContentCollectionLink
import com.mentionme.lib.Objects.MentionmeReferrer

import com.mentionme.mentionme.R
import kotlinx.android.synthetic.main.fragment_referrer_not_found_or_multiple.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ReferrerNotFoundOrMultipleFragment : Fragment() {

    var onFindReferrerByNameAndEmailListener: OnFindReferrerByNameAndEmailListener? = null

    var name: String = ""
    var referrer: MentionmeReferrer? = null
    var foundMultipleReferrers: Boolean? = null
    var links: ArrayList<MentionmeContentCollectionLink>? = null

    var inflatedView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        if (inflatedView == null){
            inflatedView = inflater.inflate(R.layout.fragment_referrer_not_found_or_multiple, container, false)

            inflatedView!!.titleTextView.text = "Not Found"

            inflatedView!!.backButton.setOnClickListener {
                activity?.onBackPressed()
            }

            inflatedView!!.findThemButton.setOnClickListener {
                findThemAction()
            }

            updateUI(inflatedView!!)

        }

        return inflatedView
    }

    fun updateUI(view: View){

        foundMultipleReferrers?.let {

            if (it){

                view.textView1.text = "We know a few people called ${name}. Can you confirm which one is your friend?"
                view.textView2.text = "Please enter their email address below so that we know which ${name} to thank."
                view.nameEditText.setText(name)

            }else{

                view.textView1.text = "Thanks. Unfortunately, ${name} is not a name we recognise."
                view.textView2.text = "If you think they have signed up, please check and confirm their details below."

            }

        }

    }

    fun findThemAction(){

        val name = inflatedView!!.nameEditText.text.toString()
        val email = inflatedView!!.emailEditText.text.toString()

        onFindReferrerByNameAndEmailListener?.findReferrer(name,email)

    }

    companion object {

        fun newInstance(referrer: MentionmeReferrer?, links: ArrayList<MentionmeContentCollectionLink>?, name: String, foundMultipleReferrers: Boolean?): ReferrerNotFoundOrMultipleFragment {
            val referrerNotFoundOrMultipleFragment = ReferrerNotFoundOrMultipleFragment()
            referrerNotFoundOrMultipleFragment.links = links
            referrerNotFoundOrMultipleFragment.referrer = referrer
            referrerNotFoundOrMultipleFragment.foundMultipleReferrers = foundMultipleReferrers
            referrerNotFoundOrMultipleFragment.name = name
            return referrerNotFoundOrMultipleFragment
        }

    }

}
