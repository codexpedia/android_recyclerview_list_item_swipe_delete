package com.example.listitemswipedelete

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.app.AlertDialog


// https://medium.com/@kitek/recyclerview-swipe-to-delete-easier-than-you-thought-cff67ff5e5f6
// https://github.com/kitek/android-rv-swipe-delete
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val simpleAdapter = SimpleAdapter((1..5).map { "Item: $it" }.toMutableList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = simpleAdapter

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView.adapter as SimpleAdapter

                if (direction == ItemTouchHelper.RIGHT) {
                    AlertDialog.Builder(this@MainActivity)
                            .setMessage("Are you sure you want to remove this item?")
                            .setCancelable(false)
                            .setPositiveButton("OK", { dialog, which ->
                                adapter.removeAt(viewHolder.adapterPosition)
                            })
                            .setNegativeButton("No", {dialog, which ->
                                adapter.notifyItemChanged(viewHolder.adapterPosition)
                            })
                            .create().show()

                } else if (direction == ItemTouchHelper.LEFT) {
                    adapter.notifyItemChanged(viewHolder.adapterPosition)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        addItemBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addItemBtn -> simpleAdapter.addItem("New item")
        }
    }
}

