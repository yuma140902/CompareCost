package yuma140902.android.comparecost

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.math.roundToLong

private const val NUM_ROWS: Int = 3

class MainActivity(
) : AppCompatActivity() {

    private var rows: Array<ViewRow?> = arrayOfNulls(NUM_ROWS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rows[0] = ViewRow(
            findViewById(R.id.yenEditNumber1),
            findViewById(R.id.amountEditNumber1),
            findViewById(R.id.yenTextView1)
        ) { update() }
        rows[1] = ViewRow(
            findViewById(R.id.yenEditNumber2),
            findViewById(R.id.amountEditNumber2),
            findViewById(R.id.yenTextView2)
        ) { update() }
        rows[2] = ViewRow(
            findViewById(R.id.yenEditNumber3),
            findViewById(R.id.amountEditNumber3),
            findViewById(R.id.yenTextView3)
        ) { update() }

        findViewById<Button>(R.id.clearAllButton).setOnClickListener {
            rows.forEach { row ->
                row?.yenEdit?.text?.clear()
                row?.yenEdit?.clearFocus()
                row?.amountEdit?.text?.clear()
                row?.amountEdit?.clearFocus()
            }
        }
    }

    private fun sorted3(rows: Array<ViewRow?>): Triple<ViewRow?, ViewRow?, ViewRow?> {
        val sorted = rows.filterNotNull().filter { it.result != null }.sortedBy { it.result }
        return Triple(sorted.getOrNull(0), sorted.getOrNull(1), sorted.getOrNull(2))
    }

    private fun update() {
        rows.forEach { it?.resultView?.setBackgroundColor(Color.TRANSPARENT) }
        val triple = sorted3(rows)
        triple.first?.resultView?.setBackgroundColor(Color.rgb(0, 127, 0))
        triple.second?.resultView?.setBackgroundColor(Color.rgb(75, 127, 0))
    }
}

class ViewRow(
    val yenEdit: EditText,
    val amountEdit: EditText,
    val resultView: TextView,
    val onUpdated: (ViewRow) -> Unit
) {
    var yen: Long? = null
        private set
    var amount: Double? = null
        private set
    var result: Double? = null
        private set

    init {
        yenEdit.addTextChangedListener(SimpleTextWatcher.after { s ->
            yen = s.toString().toLongOrNull()
            update()
            onUpdated(this)
        })

        amountEdit.addTextChangedListener(SimpleTextWatcher.after { s ->
            amount = s.toString().toDoubleOrNull()
            update()
            onUpdated(this)
        })
    }

    private fun update() {
        result = yen?.div(amount ?: 1.0)
        resultView.text = formatResult(result)
    }

    private fun formatResult(result: Double?): String {
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