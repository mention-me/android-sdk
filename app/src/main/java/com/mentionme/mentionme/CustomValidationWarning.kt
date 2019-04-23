package com.mentionme.mentionme

import com.mentionme.lib.MentionmeValidationWarning

class CustomValidationWarning: MentionmeValidationWarning() {

    init {

    }

    override fun reportWarning(warning: String) {
        super.reportWarning(warning)

        //Here you can include you own analytics
    }

}