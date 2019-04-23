package Fragments


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mentionme.lib.Objects.MentionmeOffer
import com.mentionme.lib.Objects.MentionmeShareLink

import com.mentionme.mentionme.R
import kotlinx.android.synthetic.main.fragment_referrer_enrolled.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ReferrerEnrolledFragment : Fragment() {

    var inflatedView: View? = null

    var offer: MentionmeOffer? = null
    var shareLinks: ArrayList<MentionmeShareLink>? = null
    var firstname = ""
    var surname = ""

    var shareType = ShareType.email

    enum class ShareType{
        email, facebook, whatsapp, messenger, chat
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        if (inflatedView == null){
            inflatedView = inflater.inflate(R.layout.fragment_referrer_enrolled, container, false)

            updateUI(inflatedView!!)
            setClickListeners(inflatedView!!)
        }

        return inflatedView
    }

    private fun updateUI(view: View){

        view.titleTextView.setText("Share")
        view.backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        view.textView1.text = "${firstname}, simply refer one or more friends."
        val summary = offer?.refereeReward?.summary ?: ""
        view.textView2.text = "Treat them to ${summary} and get ${summary} for yourself. Now share it any way you like to get your reward."

        resetImageViews()
        updateImageViews()
        updateText()
    }

    private fun setClickListeners(view: View){

        view.emailImageView.setOnClickListener {
            resetImageViews()
            shareType = ShareType.email
            updateImageViews()
            updateText()
        }
        view.facebookImageView.setOnClickListener {
            resetImageViews()
            shareType = ShareType.facebook
            updateImageViews()
            updateText()
        }
        view.whatsappImageView.setOnClickListener {
            resetImageViews()
            shareType = ShareType.whatsapp
            updateImageViews()
            updateText()
        }
        view.messengerImageView.setOnClickListener {
            resetImageViews()
            shareType = ShareType.messenger
            updateImageViews()
            updateText()
        }
        view.chatImageView.setOnClickListener {
            resetImageViews()
            shareType = ShareType.chat
            updateImageViews()
            updateText()
        }

        view.copyButton.setOnClickListener {
            val label = getURLtoCopy()
            val text = getURLtoCopy()
            val clipboard =  (activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
            val clip = ClipData.newPlainText(label, text)
            clipboard.primaryClip = clip
        }

        view.shareButton.setOnClickListener {
            shareAction()
        }

    }

    private fun resetImageViews(){
        inflatedView?.emailImageView?.setImageResource(R.drawable.emailunselected)
        inflatedView?.facebookImageView?.setImageResource(R.drawable.fbunselected)
        inflatedView?.whatsappImageView?.setImageResource(R.drawable.whatsappunselected)
        inflatedView?.messengerImageView?.setImageResource(R.drawable.messengerunselected)
        inflatedView?.chatImageView?.setImageResource(R.drawable.chatunselected)
    }

    private fun updateImageViews(){

        when (shareType){
            ShareType.email -> inflatedView?.emailImageView?.setImageResource(R.drawable.emailselected)
            ShareType.facebook -> inflatedView?.facebookImageView?.setImageResource(R.drawable.fbselected)
            ShareType.whatsapp -> inflatedView?.whatsappImageView?.setImageResource(R.drawable.whatsappselected)
            ShareType.messenger -> inflatedView?.messengerImageView?.setImageResource(R.drawable.messengerselected)
            ShareType.chat -> inflatedView?.chatImageView?.setImageResource(R.drawable.chatselected)
        }
    }

    private fun updateText(){

        when (shareType){
            ShareType.email -> {
                inflatedView?.shareTitleTextView?.text = "Share by Email"
                inflatedView?.shareButton?.setText("SEND VIA MY EMAIL")
                inflatedView?.shareDescriptionTextView?.text = formatText("link")
                inflatedView?.linkTextView?.text = getLinkText("link")
            }
            ShareType.facebook -> {
                inflatedView?.shareTitleTextView?.text = "By Facebook"
                inflatedView?.shareButton?.setText("SHARE")
                inflatedView?.shareDescriptionTextView?.text = getDescriptionTextforType("facebook")
                inflatedView?.linkTextView?.text = getLinkText("facebook")
            }
            ShareType.whatsapp -> {
                inflatedView?.shareTitleTextView?.text = "Share By WhatsApp"
                inflatedView?.shareButton?.setText("SEND VIA WHATSAPP")
                inflatedView?.shareDescriptionTextView?.text = getDescriptionTextforType("whatsapp")
                inflatedView?.linkTextView?.text = getLinkText("whatsapp")
            }
            ShareType.messenger -> {
                inflatedView?.shareTitleTextView?.text = "By Messenger"
                inflatedView?.shareButton?.setText("SEND ON FACEBOOK MESSENGER")
                inflatedView?.shareDescriptionTextView?.text = getDescriptionTextforType("facebookmessengermobile")
                inflatedView?.linkTextView?.text = getLinkText("facebookmessengermobile")
            }
            ShareType.chat -> {
                inflatedView?.shareTitleTextView?.text = "Tell them in person"
                inflatedView?.shareButton?.setText("LET ME SHARE USING MY NAME")
                inflatedView?.shareDescriptionTextView?.text = "Friends can simply enter your name at checkout to get 20% off"
                inflatedView?.linkTextView?.text = getLinkText("link")
            }
        }

    }

    private fun getLinkText(type: String): String{

        shareLinks?.let {
            it.forEach { shareLink ->
                if (shareLink.type == type){
                    return shareLink.url
                }
            }
        }

        return ""
    }

    private fun getURLtoCopy(): String{
        when (shareType){
            ShareType.email -> return getLinkText("link")
            ShareType.facebook -> return getLinkText("facebook")
            ShareType.whatsapp -> return getLinkText("whatsapp")
            ShareType.messenger -> return getLinkText("facebookmessengermobile")
            ShareType.chat -> return getLinkText("link")
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

    private fun formatText(type: String): String{
        val t = getDescriptionTextforType(type)
        if (t == ""){
            return getDescriptionTextforType("facebook")
        }else{
            return t
        }
    }

    private fun getDescriptionTextforType(type: String): String{

        shareLinks?.let {
            it.forEach { shareLink ->
                if (shareLink.type == type){
                    return shareLink.defaultShareMessage
                }
            }
        }

        return ""
    }

    companion object {

        fun newInstance(offer: MentionmeOffer?, shareLinks: ArrayList<MentionmeShareLink>?, firstname: String, surname: String): ReferrerEnrolledFragment {
            val referrrerEnrolled = ReferrerEnrolledFragment()
            referrrerEnrolled.offer = offer
            referrrerEnrolled.shareLinks = shareLinks
            referrrerEnrolled.firstname = firstname
            referrrerEnrolled.surname = surname
            return referrrerEnrolled
        }

    }

}
