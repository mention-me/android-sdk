package Fragments


import Utilities.OnButtonClickedListener
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mentionme.mentionme.R
import kotlinx.android.synthetic.main.fragment_referrer.*
import kotlinx.android.synthetic.main.fragment_referrer.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ReferrerFragment : Fragment() {

    var onButtonClickedListener: OnButtonClickedListener? = null

    var inflatedView: View? = null
    var hiddenContainerView = false
    var hiddenWarningView = false
    var privacyTermsString = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        if (inflatedView == null){
            inflatedView = inflater.inflate(R.layout.fragment_referrer, container, false)

            inflatedView?.inviteButton?.setOnClickListener {
                onButtonClickedListener?.buttonClicked()
            }

            inflatedView?.titleTextView?.setText("Your Order is Complete")

            inflatedView?.privacyTermsTextView?.setOnClickListener {
                val uri = Uri.parse(privacyTermsString)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                val b = Bundle()
                b.putBoolean("new_window",true)
                intent.putExtras(b)
                context?.startActivity(intent)
            }
        }

        updateVisibility()

        return inflatedView
    }

    fun showContentView(){
        hiddenContainerView = false
        updateVisibility()
    }

    fun hideContentView(){
        hiddenContainerView = true
        updateVisibility()
    }

    fun showWarningView(){
        hiddenWarningView = false
        updateWarningVisibility()
    }

    fun hideWarningView(){
        hiddenWarningView = true
        updateWarningVisibility()
    }

    private fun updateVisibility(){
        if (hiddenContainerView){
            inflatedView?.container_view?.visibility = View.GONE
        }else{
            inflatedView?.container_view?.visibility = View.VISIBLE
        }
    }

    private fun updateWarningVisibility(){
        if (hiddenWarningView){
            inflatedView?.warningView?.visibility = View.GONE
        }else{
            inflatedView?.warningView?.visibility = View.VISIBLE
        }
    }

    fun updateUI(label1: String, label2: String, privacyTermsString: String){

        inflatedView?.textView1?.text = label1
        inflatedView?.textView2?.text = label2
        this.privacyTermsString = privacyTermsString

    }

    fun updateWarningUI(message: String){

        inflatedView?.messageTextView?.text = message
    }

}
