import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpaceItemDecoration : ItemDecoration {
    private val mSpace: Int
    private var mShowFirstDivider = false
    private var mShowLastDivider = false
    var mOrientation = -1

    constructor(context: Context?, attrs: AttributeSet?) {
        mSpace = 0
    }

    constructor(
        context: Context?, attrs: AttributeSet?, showFirstDivider: Boolean,
        showLastDivider: Boolean
    ) : this(context, attrs) {
        mShowFirstDivider = showFirstDivider
        mShowLastDivider = showLastDivider
    }

    constructor(spaceInPx: Int) {
        mSpace = spaceInPx
    }

    constructor(
        spaceInPx: Int, showFirstDivider: Boolean,
        showLastDivider: Boolean
    ) : this(spaceInPx) {
        mShowFirstDivider = showFirstDivider
        mShowLastDivider = showLastDivider
    }

    constructor(ctx: Context, resId: Int) {
        mSpace = ctx.resources.getDimensionPixelSize(resId)
    }

    constructor(
        ctx: Context, resId: Int, showFirstDivider: Boolean,
        showLastDivider: Boolean
    ) : this(ctx, resId) {
        mShowFirstDivider = showFirstDivider
        mShowLastDivider = showLastDivider
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (mSpace == 0) {
            return
        }
        if (mOrientation == -1) getOrientation(parent)
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION || position == 0 && !mShowFirstDivider) {
            return
        }
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.top = mSpace
            if (mShowLastDivider && position == state.itemCount - 1) {
                outRect.bottom = outRect.top
            }
        } else {
            outRect.left = mSpace
            if (mShowLastDivider && position == state.itemCount - 1) {
                outRect.right = outRect.left
            }
        }
    }

    private fun getOrientation(parent: RecyclerView): Int {
        if (mOrientation == -1) {
            mOrientation = if (parent.layoutManager is LinearLayoutManager) {
                val layoutManager = parent.layoutManager as LinearLayoutManager?
                layoutManager!!.orientation
            } else {
                throw IllegalStateException(
                    "DividerItemDecoration can only be used with a LinearLayoutManager."
                )
            }
        }
        return mOrientation
    }
}