package br.com.mxel.cuedot.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.mxel.cuedot.R

class PagedListLayout : SwipeRefreshLayout {

    var adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>?
        get() = recyclerView.adapter
        set(value) {
            recyclerView.adapter = value
        }

    private val content: FrameLayout = FrameLayout(context)

    private val recyclerView: RecyclerView = RecyclerView(context)

    private var feedbackView: View? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = null
        recyclerView.layoutManager = LinearLayoutManager(context)

        content.addView(recyclerView, layoutParams)

        addView(content, layoutParams)
    }

    override fun setRefreshing(refreshing: Boolean) {

        if (isRefreshing != refreshing) {

            recyclerView.visibility = if (refreshing) {
                View.GONE
            } else {
                View.VISIBLE
            }
            feedbackView?.visibility = View.GONE
        }
        super.setRefreshing(refreshing)
    }

    fun showFeedbackStatus(feedbackMessage: String) {

        if (feedbackView == null) {
            feedbackView = createFeedbackView()
            content.addView(feedbackView)
        }

        feedbackView?.findViewById<AppCompatTextView>(R.id.feedback_text_view)?.text = feedbackMessage
        feedbackView?.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun createFeedbackView(): View {
        return View.inflate(context, R.layout.error_default_layout, null)
    }
}