package Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mentionme.mentionme.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
open class BackStackFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_back_stack, container, false)
    }

    companion object {

        fun handleBackPressed(fm: FragmentManager): Boolean {
            if (fm.getFragments() != null) {
                for (frag in fm.getFragments()) {
                    if (frag != null && frag!!.isVisible() && frag is BackStackFragment) {
                        if ((frag as BackStackFragment).onBackPressed()) {
                            return true
                        }
                    }
                }
            }
            return false
        }

    }

    protected fun onBackPressed(): Boolean {
        val fm = childFragmentManager
        if (handleBackPressed(fm)) {
            return true
        } else if (userVisibleHint && fm.backStackEntryCount > 0) {
            fm.popBackStack()
            return true
        }
        return false
    }


}
