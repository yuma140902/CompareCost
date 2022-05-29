package yuma140902.android.comparecost

import android.widget.EditText
import android.widget.TextView
import kotlin.math.roundToLong

data class RowModel(var yen: Long? = null, var amount: Double? = null) {
    fun calcResult(): Double? {
        return yen?.div(amount ?: 1.0)
    }
}

class RowViewLogic(
    val yenEdit: EditText,
    val amountEdit: EditText,
    val resultView: TextView,
    val model: RowModel,
    val onUpdated: (RowViewLogic) -> Unit
) {
    init {
        yenEdit.addTextChangedListener(SimpleTextWatcher.after { s->
            model.yen = s.toString().toLongOrNull()
            updateView()
            onUpdated(this)
        })

        amountEdit.addTextChangedListener(SimpleTextWatcher.after { s->
            model.amount = s.toString().toDoubleOrNull()
            updateView()
            onUpdated(this)
        })
    }

    fun updateView() {
        resultView.text = formatResult(model.calcResult())
    }

    fun clearView() {
        yenEdit.text.clear()
        yenEdit.clearFocus()
        amountEdit.text.clear()
        amountEdit.clearFocus()
    }

    companion object {
        fun formatResult(result: Double?): String {
            return if (result == null) {
                ""
            } else {
                if (result.isInfinite() || result.isNaN()) {
                    ""
                } else if (result < 1.0) {
                    String.format("%.2f", result)
                } else {
                    result.roundToLong().toString()
                }
            }
        }
    }
}