package yuma140902.android.comparecost

import android.text.TextWatcher

interface SimpleTextWatcher: TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //empty
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //empty
    }
}