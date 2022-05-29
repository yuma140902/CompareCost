package yuma140902.android.comparecost

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

private const val NUM_ROWS: Int = 3

fun isPlain(d: Double?): Boolean {
    return d != null && !d.isNaN() && !d.isInfinite()
}

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ListAdapter(this) { update() }
        for (_i in 0 until NUM_ROWS) {
            adapter.add(RowItem(RowModel()))
        }

        val mainListView = findViewById<ListView>(R.id.main_list)
        mainListView.adapter = adapter

        findViewById<Button>(R.id.clearAllButton).setOnClickListener {
            for (i in 0 until adapter.count) {
                val item = adapter.getItem(i)
                item?.viewLogic?.clearView()
            }
        }
    }

    private fun sorted3(rows: Array<RowItem?>): Triple<RowItem?, RowItem?, RowItem?> {
        val sorted = rows.filterNotNull().filter { isPlain(it.model.calcResult())  }.sortedBy { it.model.calcResult() }
        return Triple(sorted.getOrNull(0), sorted.getOrNull(1), sorted.getOrNull(2))
    }

    private fun update() {
        val array: Array<RowItem?> = arrayOfNulls(adapter.count)
        for (i in 0 until adapter.count) {
            val item = adapter.getItem(i)
            item?.viewLogic?.resultView?.setBackgroundColor(Color.TRANSPARENT)
            array[i] = item
        }
        val triple = sorted3(array)
        triple.first?.viewLogic?.resultView?.setBackgroundColor(Color.rgb(0, 127, 0))
        triple.second?.viewLogic?.resultView?.setBackgroundColor(Color.rgb(75, 127, 0))
    }
}
