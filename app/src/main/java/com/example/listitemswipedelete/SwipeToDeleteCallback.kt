package com.example.listitemswipedelete

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log

abstract class SwipeToDeleteCallback(context: Context) : ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_24)
    private val intrinsicWidth = deleteIcon.intrinsicWidth
    private val intrinsicHeight = deleteIcon.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#eeeeee")

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        Log.d("SwipeToDeleteCallback", "position: dX = $dX, dY = $dY")
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        // Draw the delete background
        background.color = backgroundColor
        background.setBounds(itemView.left, itemView.top, itemView.right, itemView.bottom)
        background.draw(c)
        Log.d("SwipeToDeleteCallback", "background bound itemView.left = ${itemView.left}, itemView.top = ${itemView.top}, itemView.right = ${itemView.right}, itemView.bottom = ${itemView.bottom}")

        // Draw the delete icon
        var slidedDistance = dX.toInt()
        var width = itemView.right
        var slidedPercentage = slidedDistance / width.toFloat()
        Log.d("SwipeToDeleteCallback","slidedDistance = $slidedDistance, width = $width, slidedPercentage = $slidedPercentage")

        var iconSizePercentage = slidedPercentage
        var iconWidth = intrinsicWidth
        var iconHeight = intrinsicHeight
        // Go with the raw intrinsicWidth and intrinsicHeight after it has slided half of the total width
        if (slidedPercentage > 0 && slidedPercentage <= 0.5) {
            iconSizePercentage = (slidedPercentage / 0.5).toFloat()
            iconWidth = (iconWidth * iconSizePercentage).toInt()
            iconHeight = (iconHeight * iconSizePercentage).toInt()
        }
        Log.d("SwipeToDeleteCallback","final slidedPercentage = $slidedPercentage, iconSizePercentage = $iconSizePercentage")

        // Calculate position of delete icon
        var deleteIconTop = itemView.top + (itemHeight - iconHeight) / 2
        var deleteIconMargin = (itemHeight - iconHeight) / 2
        var deleteIconLeft = itemView.left + deleteIconMargin
        var deleteIconRight = itemView.left + deleteIconMargin + iconWidth
        var deleteIconBottom = deleteIconTop + iconWidth

        Log.d("SwipeToDeleteCallback","deleteIconLeft = $deleteIconLeft, deleteIconTop = $deleteIconTop, deleteIconRight = $deleteIconRight, deleteIconBottom = $deleteIconBottom")
        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteIcon.draw(c)

        var newDx = dX
        if (newDx <= -100f) {
            // if swipe left, make the swipe distance shorter
            newDx = -100f
        }
        super.onChildDraw(c, recyclerView, viewHolder, newDx, dY, actionState, isCurrentlyActive)
    }


}
