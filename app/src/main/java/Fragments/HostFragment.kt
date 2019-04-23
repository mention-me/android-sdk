package Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
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
class HostFragment : BackStackFragment() {

    private var fragment: Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_host, container, false)
        fragment?.let {
            replaceFragment(it, false)
        }

        return view
    }

    fun getVisibleFragment(): Fragment{
        return fragment!!
    }

    fun replaceFragment(fragment: Fragment, addToBackstack: Boolean) {
        if (addToBackstack) {
            childFragmentManager.beginTransaction().replace(R.id.hosted_fragment, fragment).addToBackStack(null)
                .commit()
        } else {
            childFragmentManager.beginTransaction().replace(R.id.hosted_fragment, fragment).commit()
        }
    }

    companion object {

        fun newInstance(fragment: Fragment): HostFragment {
            val hostFragment = HostFragment()
            hostFragment.fragment = fragment
            return hostFragment
        }

    }

}
