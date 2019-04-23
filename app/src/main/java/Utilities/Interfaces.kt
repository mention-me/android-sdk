package Utilities

import com.mentionme.lib.Objects.MentionmeContentCollectionLink
import com.mentionme.lib.Objects.MentionmeReferrer

class Interfaces{

}

interface OnButtonClickedListener{
    fun buttonClicked()
}


interface OnFindReferrerByNameListener{
    fun findReferrer(name: String)
}

interface OnFindReferrerByNameAndEmailListener{
    fun findReferrer(name: String, email: String)
}

interface OnGetOffLinkCustomerListener{
    fun linkCustomer(email: String,firstname: String)
}
