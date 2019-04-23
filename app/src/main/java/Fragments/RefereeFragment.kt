package Fragments


import Utilities.OnFindReferrerByNameListener
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mentionme.mentionme.R
import kotlinx.android.synthetic.main.fragment_referee.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RefereeFragment : Fragment() {

    var onFindReferrerByNameListener: OnFindReferrerByNameListener? = null
    var inflatedView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        if (inflatedView == null){
            inflatedView = inflater.inflate(R.layout.fragment_referee, container, false)

            inflatedView!!.titleTextView.text = "Referee"

            inflatedView!!.findThemButton.setOnClickListener {
                findThemAction()
            }

        }

        return inflatedView
    }

    fun findThemAction(){

        val text = inflatedView?.editText?.text?.toString() ?: ""
        onFindReferrerByNameListener?.findReferrer(text)

    }




}
