package lcleite.github.com.helloandroidkotlin.view.list

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class SearchResultItemDivider : RecyclerView.ItemDecoration {

    private var divider: Drawable

    constructor(divider: Drawable) {
        this.divider = divider
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left: Int = parent.paddingLeft
        val right: Int = parent.width - parent.paddingRight
        val childCount: Int = parent.childCount

        for(i in 0 until childCount){
            val child: View = parent.getChildAt(i)
            val params: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams
            val top: Int = child.bottom + params.bottomMargin
            val bottom: Int = top + divider.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
    }
}
