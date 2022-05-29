package yuma140902.android.comparecost

import android.text.Editable
import android.text.TextWatcher

interface SimpleTextWatcher : TextWatcher {
    companion object Factory {
        fun after(f: (s: Editable?) -> Unit): SimpleTextWatcher {
            return object : SimpleTextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    f(s)
                }
            }
        }

        fun before(f: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit): SimpleTextWatcher {
            return object : SimpleTextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    f(s, start, count, after)
                }
            }
        }

        fun on(f: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit): SimpleTextWatcher {
            return object : SimpleTextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    f(s, start, before, count)
                }
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //empty
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //empty
    }

    override fun afterTextChanged(s: Editable?) {
        //empty
    }
}